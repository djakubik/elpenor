package elpenor.szkielet;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Umożliwia zapisanie strumienia bitowego do strumienia bajtów. Poszczególne
 * bity są pakowane do bajtów a nastepnie wysyłane do strumienia wyjściowego.
 * 
 * @author Mikołaj Izdebski
 */
public class KoderBitowy
{
	private long buff;
	private int live;
	private final OutputStream out;

	/**
	 * Tworzy nowy koder bitowy.
	 * 
	 * @param out
	 *          strumien wyjściowy, do którego będą sapisywane dane
	 */
	public KoderBitowy(OutputStream out)
	{
		this.out = out;
	}

	/**
	 * Zapisuje bity do strumienia.
	 * 
	 * @param v
	 *          wartość do zapisania (brane są namłodsze bity)
	 * @param n
	 *          liczba bitów do zapisania
	 * @throws IOException
	 *           jeżeli metoda write() strumienia wyjściowego wyrzuciła tenże
	 *           wyjątek
	 */
	public void put(int v, int n) throws IOException
	{
		live += n;
		buff |= (v & 0xFFFFFFFFL) << (64 - live);

		while (live >= 8)
		{
			out.write((int) (buff >>> (64 - 8)));
			live -= 8;
			buff <<= 8;
		}
	}

	/**
	 * Opróżnia zbuforowane bity tak, że wszystkie dotychczas zapisane bity są
	 * wysyłane do strumienia wyjściowego. Oznacza to dodanie dodatkowch zerowch
	 * bitów w celu zaokrąglenia do rozmiaru bajta (tylko w przypadku, gdy
	 * dotychczas zapisana liczba bitów nie jest wielokrotnością bajta).
	 * 
	 * @throws IOException
	 *           jeżeli metoda write() strumienia wyjściowego wyrzuci
	 *           <code>IOException</code>
	 */
	public void flush() throws IOException
	{
		if (live > 0)
			put(0, 8 - live);
		assert (live == 0);
	}
	
	public void put_fib(int n) throws IOException
	{
		put(n, 24);
	}
}
