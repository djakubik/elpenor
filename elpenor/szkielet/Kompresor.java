package elpenor.szkielet;

import java.util.Map;

/**
 * @author Mikołaj Izdebski
 */
class Kompresor extends Watek
{
	private final Bufor<Dane> bwe;
	private final Bufor<Dane> bwy;
	private final Kodek k;
	private final Map<String, Integer> par;

	public Kompresor(StosKompresji sk, Bufor<Dane> bwe, Bufor<Dane> bwy,
			Kodek k, Map<String, Integer> par)
	{
		super(sk);
		this.bwe = bwe;
		this.bwy = bwy;
		this.k = k;
		this.par = par;
	}

	void robota() throws InterruptedException
	{
		Dane t;
		while ((t = bwe.pobierz()) != null)
		{
			bwy.dodaj(k.zakoduj(t,par));
		}
		bwy.dodaj(null);
	}
}
