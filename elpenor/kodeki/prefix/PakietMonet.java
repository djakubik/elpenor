package elpenor.kodeki.prefix;

/**
 * Ta klasa reprezentuje pakiet monet. Każda z monet posiada nominał bedący
 * całkowitą ujemną potegą dwójki oraz wartość numizmatyczną będącą dowolną
 * liczbą naturalną.
 * <p>
 * Pojedyncze monety traktowane są jak pakiety składajace się z jednej monety, a
 * więc także mogą być przechowywane w tej klasie.
 * 
 * @author Mikołaj Izdebski
 */
class PakietMonet
{
	private final long waga;
	private final long[] pakiet = new long[3];

	/**
	 * Tworzy nowy, pusty pakiet. Pakiet ten nie zawiera żadnych monet.
	 */
	PakietMonet()
	{
		waga = 0;
	}

	/**
	 * Utwórz pakiet składający się z tylko jednej monety o określonej wartości
	 * numizmatycznej i nominale równym 2^-glebokosc.
	 * 
	 * @param waga
	 *          wartość numizmatyczna monety
	 * @param glebokosc
	 *          minus logarytm dwójkowy z nominału monety
	 */
	PakietMonet(long waga, int glebokosc)
	{
		int indeks, przesuniecie;

		assert (glebokosc > 0);

		this.waga = waga;
		this.pakiet[0] = 0;
		this.pakiet[1] = 0;
		this.pakiet[2] = 0;

		glebokosc--;
		indeks = glebokosc / 7;
		przesuniecie = glebokosc % 7 * 9;
		this.pakiet[indeks] = 1L << przesuniecie;
	}

	/**
	 * Otwórz nowy pakiet monet, powstały z połączenia dwóch innych pakietów.
	 * 
	 * @param p1
	 *          jeden z pakietów do połączenia
	 * @param p2
	 *          drugi z pakietów do połączenia
	 */
	PakietMonet(PakietMonet p1, PakietMonet p2)
	{
		this.waga = p1.waga + p2.waga;
		this.pakiet[0] = p1.pakiet[0] + p2.pakiet[0];
		this.pakiet[1] = p1.pakiet[1] + p2.pakiet[1];
		this.pakiet[2] = p1.pakiet[2] + p2.pakiet[2];
	}

	/**
	 * Zwraca wartość numizmatyczną monety.
	 * 
	 * @return wartość numizmatyczna monety
	 */
	long waga()
	{
		return waga;
	}

	/**
	 * Znaleźlismy optymalne rozwiązanie, jest ono zapakowane w wektorze s_c. Czas
	 * rozpakować je.
	 * 
	 * @return
	 */
	int[] rozpakuj()
	{
		int[] C = new int[21];
		int u, v;

		long s_c0 = pakiet[0];
		long s_c1 = pakiet[1];
		long s_c2 = pakiet[2];

		u = (int) (s_c2 >> 5 * 9) & 0x1FF;
		C[20] = u;
		v = (int) (s_c2 >> 4 * 9) & 0x1FF;
		C[19] = v - u;
		u = (int) (s_c2 >> 3 * 9) & 0x1FF;
		C[18] = u - v;
		v = (int) (s_c2 >> 2 * 9) & 0x1FF;
		C[17] = v - u;
		u = (int) (s_c2 >> 9) & 0x1FF;
		C[16] = u - v;
		v = (int) (s_c2) & 0x1FF;
		C[15] = v - u;
		u = (int) (s_c1 >> 6 * 9) & 0x1FF;
		C[14] = u - v;
		v = (int) (s_c1 >> 5 * 9) & 0x1FF;
		C[13] = v - u;
		u = (int) (s_c1 >> 4 * 9) & 0x1FF;
		C[12] = u - v;
		v = (int) (s_c1 >> 3 * 9) & 0x1FF;
		C[11] = v - u;
		u = (int) (s_c1 >> 2 * 9) & 0x1FF;
		C[10] = u - v;
		v = (int) (s_c1 >> 9) & 0x1FF;
		C[9] = v - u;
		u = (int) (s_c1) & 0x1FF;
		C[8] = u - v;
		v = (int) (s_c0 >> 6 * 9) & 0x1FF;
		C[7] = v - u;
		u = (int) (s_c0 >> 5 * 9) & 0x1FF;
		C[6] = u - v;
		v = (int) (s_c0 >> 4 * 9) & 0x1FF;
		C[5] = v - u;
		u = (int) (s_c0 >> 3 * 9) & 0x1FF;
		C[4] = u - v;
		v = (int) (s_c0 >> 2 * 9) & 0x1FF;
		C[3] = v - u;
		u = (int) (s_c0 >> 9) & 0x1FF;
		C[2] = u - v;
		v = (int) (s_c0) & 0x1FF;
		C[1] = v - u;
		C[0] = 0;

		return C;
	}
}
