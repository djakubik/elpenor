/*
 * Ogólnie o implementacji:
 * - pełna wielowątkowość
 * - brak metod synchronizowanych, synchronizacja opiera się na semaforach
 */

package elpenor.szkielet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import elpenor.kodeki.Cezar;
import elpenor.kodeki.GaderyPoluki;
import elpenor.kodeki.LZ77;
import elpenor.kodeki.MTF;
import elpenor.kodeki.prefix.Huffman;

/**
 * @author Mikołaj Izdebski
 */
public class Silnik
{
	/**
	 * Tworzy nowy silnik kompresji.
	 */
	public Silnik()
	{
	}

	private static final Collection<Kodek> kodeki;
	static
	{
		final Vector<Kodek> vec = new Vector<Kodek>();

		vec.add(new Huffman());
		vec.add(new GaderyPoluki());
		vec.add(new MTF());
		vec.add(new Cezar());
                vec.add(new LZ77());

		Set<Integer> set = new TreeSet<Integer>();

		/* Upewnij się, że wszystkie kodeki mają unikalny identyfikator. */
		for (Kodek k : vec)
		{
			boolean rv = set.add(k.id());
			assert (rv);
		}

		kodeki = Collections.unmodifiableCollection(vec);
	}

	/**
	 * Zwraca listę dostępnych kodeków.
	 * 
	 * @return kolekcja zawierająca wszystkie dostępne kodeki
	 */
	public Collection<Kodek> kodeki()
	{
		return kodeki;
	}

	/**
	 * Kompresuje wskazany plik wejściowy za pomocą podanych algorytmów kompresji
	 * i zapisuje wynik we wskazanym pliku wynikowym.
	 * 
	 * @param plik_we
	 *          nazwa pliku wejścowego (może zawierać pełną ścieżkę)
	 * @param plik_wy
	 *          nazwa pliku wynikowego (może zawierać pełną ścieżkę)
	 * @return <code>true</code> jeśli kompresja powiodła się, <code>false</code>
	 *         jeżeli kompresja nie powiodła się
	 * @throws IOException
	 */
	public boolean kompresuj(String plik_we, String plik_wy,
			Collection<KodekSparametryzowany> kodeki) throws IOException
	{
		FileInputStream fin = new FileInputStream(plik_we);
		FileOutputStream fout = new FileOutputStream(plik_wy);
		StosKompresji sk = new StosKompresji();
		return sk.kompresuj(fin, fout, kodeki);
	}

	public DaneSkompresowane wczytaj_dane_skompresowane(String plik_we)
			throws FileNotFoundException
	{
		FileInputStream fin = new FileInputStream(plik_we);

		Metadane md;
		try
		{
			md = Metadane.wczytaj(fin);
		}
		catch (Exception exc1)
		{
			return null;
		}

		List<Kodek> ks = new LinkedList<Kodek>();
		int rozm_we, rozm_wy;

		try
		{
			int n_mod = md.pobierz_wartosc();
			for (int i = 0; i < n_mod; i++)
			{
				int id = md.pobierz_wartosc();

				Kodek k0 = null;

				for (Kodek k : kodeki)
				{
					if (k.id() == id)
					{
						k0 = k;
						break;
					}
				}

				if (k0 == null)
					return null;

				ks.add(0, k0);
				System.out.printf("Kodek: %s\n", k0.nazwa());
			}

			rozm_we = md.pobierz_wartosc();
			System.out.printf("rozm_we=%d%n", rozm_we);
			rozm_wy = md.pobierz_wartosc();
			System.out.printf("rozm_wy=%d%n", rozm_wy);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return null;
		}
		
		return new DaneSkompresowane(fin, ks, md, rozm_we, rozm_wy);
	}
}
