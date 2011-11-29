package elpenor.kodeki;

import java.io.PrintWriter;
import java.util.Map;

import elpenor.szkielet.Kodek;
import elpenor.szkielet.Dane;

/**
 * @author Mikołaj Izdebski
 */
public class MTF extends Kodek
{
	private static final int ID = 4;

	public int id()
	{
		return ID;
	}

	public String opis()
	{
		return "Move To Front";
	}

	public String nazwa()
	{
		return "MTF";
	}

	public Dane zakoduj(Dane b, Map<String, Integer> par)
	{
		byte[] buf = b.bufor();
		int lim = b.offset() + b.length();

		int[] yy = new int[256];
		for (int i = 0; i < 256; i++)
			yy[i] = i;

		for (int off = b.offset(); off < lim; off++)
		{
			int ch = buf[off] & 0xFF;
			int v = yy[ch];
			buf[off] = (byte) v;
			for (int j = 0; j < 256; ++j)
				if (yy[j] < v)
					yy[j]++;
			yy[ch] = 0;
		}

		return b;
	}

	public Dane odkoduj(Dane b, PrintWriter pw)
	{
		byte[] buf = b.bufor();
		int lim = b.offset() + b.length();

		int[] yy = new int[256];
		for (int i = 0; i < 256; i++)
			yy[i] = i;

		for (int off = b.offset(); off < lim; off++)
		{
			int ch = buf[off] & 0xFF;
			int v = yy[ch];
			buf[off] = (byte) v;
			for (int j = ch; j > 0; j--)
				yy[j] = yy[j - 1];
			yy[0] = v;
		}

		return b;
	}
}
