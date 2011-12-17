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
		return "Move To Front – prosta transformacja strumienia danych,"
                        + " używana jako część niektórych procesów kompresji, "
                        + "której zastosowanie może spowodować zmniejszenie "
                        + "entropii. Co za tym idzie, algorytmy kompresji "
                        + "zależne od tej własności (kodowanie Shannona, "
                        + "Shannona-Fano, Huffmana, arytmetyczne) dadzą lepsze "
                        + "wyniki; może także wyprodukować sekwencje lepiej "
                        + "kompresowane metodą RLE. To, czy zastosowanie MTF "
                        + "rzeczywiście doprowadzi do zmniejszenia entropii, "
                        + "zależy od danych – zmniejszenie entropii występuje, "
                        + "gdy częstotliwości występowania symboli wykazują "
                        + "lokalną spójność. Jak wspomniano, algorytm kodowania"
                        + " i dekodowania jest bardzo prosty, dlatego jego "
                        + "implementacje są bardzo efektywne.";
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
