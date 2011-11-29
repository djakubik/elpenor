package elpenor.szkielet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Pisarz jest wątkiem typu "I/O bound", którego zadaniem jest pobranie danych z
 * bufora wejściowego i zapisanie ich do strumienia wyjściowego.
 * 
 * @author Mikołaj Izdebski
 */
public class Pisarz extends Watek
{
	private final Bufor<Dane> bw;
	private final FileChannel fc;
	private final Metadane md;

	/**
	 * Tworzy nowyego pisarza.
	 * 
	 * @param sk
	 *          stos kompresji który ma zostać powiadomiony o pomyslnym lub
	 *          niepomyslnym zapisaniu danych
	 * 
	 * @param bw
	 *          bufor z którego mają byc pobrane dane
	 * @param fc
	 *          strumień wyjściowy do którego mają być zpisane dane
	 */
	public Pisarz(StosKompresji sk, Bufor<Dane> bw, FileChannel fc)
	{
		this(sk, bw, fc, null);
	}

	/**
	 * Tworzy nowyego pisarza.
	 * 
	 * @param sk
	 *          stos kompresji który ma zostać powiadomiony o pomyslnym lub
	 *          niepomyslnym zapisaniu danych
	 * 
	 * @param bw
	 *          bufor z którego mają byc pobrane dane
	 * @param fc
	 *          strumień wyjściowy do którego mają być zpisane dane
	 * @param md
	 *          metadane, które mają byc dodatkowo zapisane do strumienia
	 *          wyjściowego
	 */
	public Pisarz(StosKompresji sk, Bufor<Dane> bw, FileChannel fc, Metadane md)
	{
		super(sk);
		this.bw = bw;
		this.fc = fc;
		this.md = md;
	}

	void robota() throws InterruptedException
	{
		try
		{
			Dane t = bw.pobierz();
			Dane t1 = bw.pobierz();
			assert (t1 == null);
			ByteBuffer bb = ByteBuffer.wrap(t.bufor());
			bb.position(t.offset());
			bb.limit(t.offset() + t.length());
			if (md != null)
			{
				md.dodajWartosc(t.length());
				md.dodaj(t.meta());
				ByteArrayOutputStream mos = new ByteArrayOutputStream();
				md.zapisz(mos);
				fc.write(ByteBuffer.wrap(mos.toByteArray()));
			}
			fc.write(bb);
			fc.force(true);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
