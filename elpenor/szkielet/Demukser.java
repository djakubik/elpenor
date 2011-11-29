package elpenor.szkielet;

/**
 * @author Mikołaj Izdebski
 */
public class Demukser extends Watek
{
	private Bufor<Dane> bwe;
	private Bufor<Dane> bwy;
	private int bs;

	public Demukser(StosKompresji sk, Bufor<Dane> bwe, Bufor<Dane> bwy, int bs)
	{
		super(sk);
		this.bwe = bwe;
		this.bwy = bwy;
		this.bs = bs;
	}

	void robota() throws InterruptedException
	{
		Dane t;

		while ((t = bwe.pobierz()) != null)
		{
			int avl = t.length();
			for (int ofs = 0; avl > 0; ofs += bs, avl -= bs)
				bwy.dodaj(new Dane(t.bufor(), ofs, Math.min(avl, bs)));
		}

		bwy.dodaj(null);
	}
}
