package elpenor.szkielet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;
import java.util.zip.DataFormatException;

/**
 * @author Mikołaj Izdebski
 */
public class Metadane
{
	private static final int MAGIC = 786217;
	private static final int VER = 0;

	private final List<Integer> vec = new Vector<Integer>();
	
	public Metadane()
	{
	}
	
	public Metadane(Metadane rhs)
	{
		dodaj(rhs);
	}
	
	public void dodaj(Metadane rhs)
	{
		vec.addAll(rhs.vec);
	}
	
	public void dodajWartosc(int v)
	{
		vec.add(v);
	}
	
	public int pobierz_wartosc()
	{
		return vec.remove(0);
	}
	

	public void zapisz(OutputStream out) throws IOException
	{
		KoderBitowy kb = new KoderBitowy(out);
		kb.put_fib(MAGIC);
		kb.put_fib(VER);
		kb.put_fib(vec.size());
		for (int i : vec)
			kb.put_fib(i);
	}
	
	public static Metadane wczytaj(InputStream in) throws DataFormatException, IOException
	{
		Metadane md = new Metadane();
		DekoderBitowy dkb = new DekoderBitowy(in);
		int magic = dkb.get_fib();
		if (magic != MAGIC)
			throw new DataFormatException("niepoprawna liczna magiczna");
		int ver = dkb.get_fib();
		if (ver > VER)
			throw new DataFormatException("wersja pliku wyjściowego jest nowsza niż wersja aplikacji");
		int n = dkb.get_fib();
		while (n-- > 0)
			md.dodajWartosc(dkb.get_fib());
		return md;
	}
}
