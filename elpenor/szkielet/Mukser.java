package elpenor.szkielet;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Mikołaj Izdebski
 */
public class Mukser extends Watek
{
	private final Bufor<Dane> bwe;
	private final Bufor<Dane> bwy;
	private final List<Dane> v = new LinkedList<Dane>();

	public Mukser(StosKompresji sk, Bufor<Dane> bwe, Bufor<Dane> bwy)
	{
		super(sk);
		this.bwe = bwe;
		this.bwy = bwy;
	}

	void robota() throws InterruptedException
	{
		Dane t;
		int ts = 0;

		while ((t = bwe.pobierz()) != null)
		{
			v.add(t);
			ts += t.offset();
		}

		byte[] buf = new byte[ts];
		int ofs = 0;

		for (Dane u : v)
		{
			System.arraycopy(u.bufor(), u.offset(), buf, ofs, u.length());
			ofs += u.length();
		}
		
		bwy.dodaj(new Dane(buf, 0, ts));
	}
}
