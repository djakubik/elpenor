package elpenor.szkielet;

import java.io.IOException;
import java.io.InputStream;

/**
 * Umozliwia odczytanie bitów ze strumienia bajtów. Klasa dualna do klasy
 * <code>KoderBitowy</code>.
 * 
 * @author Mikołaj Izdebski
 */
public class DekoderBitowy
{
	private final InputStream in;
	private long buff;
	private int live;

	/**
	 * Tworzy nowy dekoder bitowy.
	 * 
	 * @param in
	 *          strumień wejściowy, z którego maja byc pobierane dane
	 */
	public DekoderBitowy(InputStream in)
	{
		this.in = in;
	}

	/**
	 * Czyta daną liczbe bitów ze strumienia i zwraca je.
	 * 
	 * @param n
	 *          liczba bitów do odczytania ze strumienia
	 * @return bity odczytane ze strumienia
	 * @throws IOException
	 *           jeżeli metoda read() strumienia wejściowego wyrzuciła tenże
	 *           wyjątek lub napotkany został koniec pliku przed odczytaniem
	 *           wszystkich bitów
	 */
	public int get(int n) throws IOException
	{
		while (live < n)
		{
			int ch = in.read();
			if (ch < 0)
				throw new IOException("nieoczekiwany koniec pliku");
			live += 8;
			buff |= (long) ch << (64 - live);
		}

		int v = (int) ((buff >>> (64 - n)) & ((1L << n) - 1));

		buff <<= n;
		live -= n;

		return v;
	}
	
	public int get_fib() throws IOException
	{
		return get(24);
	}
}
