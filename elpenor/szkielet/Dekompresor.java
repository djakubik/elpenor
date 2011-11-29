package elpenor.szkielet;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.zip.DataFormatException;

/**
 * @author Mikołaj Izdebski
 */
class Dekompresor extends Watek
{
	private Bufor<Dane> bwe;
	private Bufor<Dane> bwy;
	private Kodek k;

	public Dekompresor(StosKompresji sk, Bufor<Dane> bwe, Bufor<Dane> bwy,
			Kodek k)
	{
		super(sk);
		this.bwe = bwe;
		this.bwy = bwy;
		this.k = k;
	}

	void robota() throws InterruptedException
	{
		Dane t;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(bos);

		try
		{
			while ((t = bwe.pobierz()) != null)
			{
				bwy.dodaj(k.odkoduj(t, pw));
			}
		}
		catch (DataFormatException exc)
		{
			exc.printStackTrace();
			throw new InterruptedException();
		}

		bos.toString();
		bwy.dodaj(null);
	}
}
