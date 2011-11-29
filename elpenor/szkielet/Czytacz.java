package elpenor.szkielet;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Czytacz jest wątkiem typu "I/O bound", którego zadaniem jest wczytanie danych
 * ze strumienia wejściowego i umieszczenie ich w buforze.
 * 
 * @author Mikołaj Izdebski
 */
public class Czytacz extends Watek
{
	private final FileInputStream fc;
	private final Bufor<Dane> bw;
	private final Metadane md;

	/**
	 * Tworzy nowy wątek czytacza.
	 * 
	 * @param sk
	 *          stos kompresji który ma zostać powiadomiony o pomyslnym lub
	 *          niepomyslnym wczytaniu danych
	 * @param fc
	 *          strumień wejściowy z którego maja być wczytyane dane
	 * @param bw
	 *          bufor do którego mają być zapisane dane
	 * @param md
	 *          metadane, które mają być dołączone do danych
	 */
	public Czytacz(StosKompresji sk, FileInputStream fc, Bufor<Dane> bw,
			Metadane md)
	{
		super(sk);
		this.fc = fc;
		this.bw = bw;
		this.md = md;
	}

	/**
	 * Tworzy nowy wątek czytacza.
	 * 
	 * @param sk
	 *          stos kompresji który ma zostać powiadomiony o pomyslnym lub
	 *          niepomyslnym wczytaniu danych
	 * @param fc
	 *          strumień wejściowy z którego maja być wczytyane dane
	 * @param bw
	 *          bufor do którego mają być zapisane dane
	 * @param md
	 *          metadane, które mają być dołączone do danych
	 */
	public Czytacz(StosKompresji sk, FileInputStream fc, Bufor<Dane> bw)
	{
		this(sk, fc, bw, null);
	}

	void robota() throws InterruptedException
	{
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			int rv;
			for (;;)
			{
				rv = fc.read(buf, 0, buf.length);
				if (rv == -1)
					break;
				bos.write(buf, 0, rv);
			}
			bos.flush();
			byte[] bb = bos.toByteArray();
			Dane t = new Dane(bb, 0, bb.length);
			if (md != null)
				t.dodajMeta(md);
			bw.dodaj(t);
			bw.dodaj(null);
		}
		catch (IOException e)
		{
			throw new InterruptedException();
		}
	}
}
