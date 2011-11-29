package elpenor.szkielet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author Mikołaj Izdebski
 */
public class StosKompresji
{
	private final Semaphore robota = new Semaphore(0);
	private Thread watek;

	public StosKompresji()
	{
	}

	void watek_zakonczony(boolean sukces)
	{
		if (!sukces)
			watek.interrupt();
		robota.release();
	}

	public boolean kompresuj(FileInputStream fin, FileOutputStream fout,
			Collection<KodekSparametryzowany> kodeki) throws IOException
	{
		FileChannel in_chan = fin.getChannel();
		FileChannel out_chan = fout.getChannel();

		watek = Thread.currentThread();
		Bufor<Dane> potok = new Bufor<Dane>();
		List<Thread> w = new LinkedList<Thread>();
		w.add(new Czytacz(this, fin, potok));
		for (KodekSparametryzowany k : kodeki)
		{
			Bufor<Dane> potok2 = new Bufor<Dane>();
			w.add(new Kompresor(this, potok, potok2, k.kodek(), k.parametry()));
			potok = potok2;
		}

		Metadane pm = new Metadane();
		pm.dodajWartosc(kodeki.size());
		for (KodekSparametryzowany k : kodeki)
			pm.dodajWartosc(k.kodek().id());
		pm.dodajWartosc((int) in_chan.size());

		w.add(new Pisarz(this, potok, out_chan, pm));

		for (Thread t : w)
			t.start();

		try
		{
			robota.acquire(kodeki.size() + 2);
			return true;
		}
		catch (InterruptedException exc)
		{
			for (Thread t : w)
				t.interrupt();
			robota.acquireUninterruptibly(kodeki.size() + 2);
			return false;
		}
	}

	public boolean dekompresuj(FileInputStream fin, FileOutputStream fout,
			Collection<Kodek> kodeki, Metadane md)
	{
		FileChannel out_chan = fout.getChannel();

		watek = Thread.currentThread();
		Bufor<Dane> potok = new Bufor<Dane>();
		List<Thread> w = new LinkedList<Thread>();
		w.add(new Czytacz(this, fin, potok, md));

		for (Kodek k : kodeki)
		{
			Bufor<Dane> potok2 = new Bufor<Dane>();
			w.add(new Dekompresor(this, potok, potok2, k));
			potok = potok2;
		}
		w.add(new Pisarz(this, potok, out_chan));

		for (Thread t : w)
			t.start();

		try
		{
			robota.acquire(kodeki.size() + 2);
			return true;
		}
		catch (InterruptedException exc)
		{
			for (Thread t : w)
				t.interrupt();
			robota.acquireUninterruptibly(kodeki.size() + 2);
			return false;
		}
	}
}
