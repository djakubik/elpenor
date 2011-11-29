package elpenor.kodeki;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import elpenor.szkielet.Kodek;
import elpenor.szkielet.Dane;
import elpenor.szkielet.Metadane;

/**
 * @author Mikołaj Izdebski
 */
public class Cezar extends Kodek
{
	private static final int AS = ('z' - 'a' + 1);
	private static final int ID = 1;
	
	public int id()
	{
		return ID;
	}

	public String nazwa()
	{
		return "Cezar";
	}

	public String opis()
	{
		return "(to chyba oczywiste co ten moduł robi)";
	}

	private void zakoduj(Dane b, int k)
	{
		int lo = b.offset();
		int hi = lo + b.length();
		byte[] bb = b.bufor();

		for (int i = lo; i < hi; i++)
		{
			int zn = bb[i] & 0xff;
			if (zn >= 'a' && zn <= 'z')
				zn = (zn - 'a' + k) % AS + 'a';
			else if (zn >= 'A' && zn <= 'Z')
				zn = (zn - 'A' + k) % AS + 'A';
			bb[i] = (byte) zn;
		}
	}

	public Dane zakoduj(Dane b, Map<String, Integer> par)
	{
		int k;
		final Integer ki = par.get("klucz");
		if (ki != null)
			k = ki;
		else
			k = 3;
		
		Metadane meta = new Metadane();
		meta.dodajWartosc(k);
		b.dodajMeta(meta);
		
		zakoduj(b, k);
		return b;
	}

	public Dane odkoduj(Dane b, PrintWriter pw)
	{
		int k = b.meta().pobierz_wartosc();
		System.out.printf("Klucz kodowania   : %2d%n", k);
		System.out.printf("Klucz dekodowania : %2d%n", AS-k);
		zakoduj(b, AS-k);
		return b;
	}

	public Collection<Parametr> parametry()
	{
		return Collections
				.singleton(new Kodek.Parametr(
						"klucz",
						"Klucz szyfrowania dla Cezara.\n\n"
								+ "Juliusz Cezar używał wartości klucza równej 3 i dlatego jest to wartość domyślna.",
						0, AS-1, 3));
	}
}
