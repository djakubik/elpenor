package elpenor.kodeki.prefix;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.zip.DataFormatException;

import elpenor.szkielet.Kodek;
import elpenor.szkielet.Dane;
import elpenor.szkielet.Metadane;

/**
 * @author Mikołaj Izdebski
 */
public class Huffman extends Kodek
{
	private static final int ID = 2;

	public int id()
	{
		return ID;
	}

	public String opis()
	{
		return "Kodowanie Huffmana - Jedna z najprostszych i łatwych "
                        + "w implementacji metod kompresji bezstratnej. Została"
                        + " opracowana w 1952 roku przez Amerykanina Davida "
                        + "Huffmana. Algorytm Huffmana nie należy do "
                        + "najefektywniejszych systemów bezstratnej kompresji "
                        + "danych, dlatego też praktycznie nie używa się go "
                        + "samodzielnie. Często wykorzystuje się go jako ostatni"
                        + " etap w różnych systemach kompresji, zarówno "
                        + "bezstratnej, jak i stratnej, np. MP3 lub JPEG. "
                        + "Pomimo, że nie jest doskonały, stosuje się go ze "
                        + "względu na prostotę oraz brak ograniczeń patentowych."
                        + " Jest to przykład wykorzystania algorytmu zachłannego.";
	}

	public String nazwa()
	{
		return "KodekHuffmana";
	}

	/** Utwórz model statystyczny rzędu zerowego dla zadanego ciągu symboli. */
	private static int[] utworz_model(byte[] buf, int off, int len)
	{
		final int[] ftab = new int[256];
		for (int i = 0; i < 256; i++)
			ftab[i] = 0;

		int lim = off + len;
		for (int i = off; i < lim; i++)
			ftab[buf[i] & 0xFF]++;

		return ftab;
	}

	public Dane zakoduj(Dane b0, Map<String, Integer> par)
	{
		final byte[] buf = b0.bufor();
		final int off = b0.offset();
		final int len = b0.length();

		int[] ftab = utworz_model(buf, off, len);
		KoderPrefiksowy koder = new KoderPrefiksowy(ftab, 256, 15);

		int koszt = 256 * 4;
		for (int i = 0; i < 256; i++)
			koszt += ftab[i] * koder.len[i];
		koszt = (koszt + 7) >> 3;

		byte[] db = new byte[koszt];
		int j = 0;
		int b = 0;
		int k = 0;

		for (int i = 0; i < 256; i += 2)
			db[j++] = (byte) ((koder.len[i] << 4) | koder.len[i + 1]);
		for (int i = 0; i < 256; i++)
			System.out.printf("L[%d]=%d%n", i, koder.len[i]);


		for (int i = 0; i < len; i++)
		{
			int sym = buf[off + i] & 0xFF;
			int cl = koder.len[sym];
			b <<= cl;
			b |= koder.code[sym];
			k += cl;
			while (k >= 8)
				db[j++] = (byte) (b >>> (k -= 8));
		}

		b <<= 8 - k;
		if (k > 0)
			db[j++] = (byte) b;

		assert (j == koszt);

		Dane dane = new Dane(db, 0, koszt);
		Metadane meta = new Metadane();
		meta.dodajWartosc(b0.length());
		dane.dodajMeta(meta);
		return dane;
	}
	
	private static boolean ge(long x, long y)
	{
		if (x < 0)
		{
			if (y < 0)
				return (x ^ 0x8000000000000000L) >= (y ^ 0x8000000000000000L);
			return true;
		}
		if (y < 0)
			return false;
		return x >= y;
	}

	public Dane odkoduj(Dane b, PrintWriter pw) throws DataFormatException
	{
		byte[] buf = b.bufor();
		int off = b.offset();
		int len = b.length();

		assert (len > 128);

		/* Odbierz tabele kodujące */
		int[] L = new int[256];
		for (int i = 0; i < 128; i++)
		{
			L[2 * i] = (buf[off + i] >> 4) & 0xF;
			L[2 * i + 1] = buf[off + i] & 0xF;
		}
		off += 128;
		
		/*for (int i = 0; i < 256; i++)
			System.out.printf("L[%d]=%d%n", i, L[i]);*/

		DekoderPrefiksowy dek = new DekoderPrefiksowy(L, 256);
		
		int n = b.meta().pobierz_wartosc();
		byte[] db = new byte[n];
		
		long v = 0;
		int w = 0;

		for (int i = 0; i < n; i++)
		{
			while (w < 15)
			{
				long ch = (off < len) ? buf[off++] & 0xFF : 0;
				w += 8;
				v |= ch << (64 - w);
			}

			/*
			 * Use a table lookup to determine minimal code length quickly. For
			 * lengths <= SW, this table always gives precise reults. For lengths >
			 * SW, additional iterations may be needed to determine the exact code
			 * length.
			 */
			int x = dek.start[(int)(v >>> (64 - DekoderPrefiksowy.SW))];
			int k = x & 0x1F;

			/*
			 * Distinguish between complete and incomplete lookup table entries. If an
			 * entry is complete, we have the symbol immediately -- it's stored in
			 * higher bits of the entry. Otherwise we need to use so called
			 * "cannonical decoding" algorithm to decode the symbol.
			 */
			int s;
			if (k <= DekoderPrefiksowy.SW)
				s = x >> 5;
			else
			{
				while (ge(v, dek.base[k + 1]))
					k++;
				s = dek.perm[dek.count[k] + (int)((v - dek.base[k]) >>> (64 - k))];
			}
			db[i] = (byte)s;

			/*
			 * At this point we know the prefix code is exactly k-bit long, so we can
			 * consume (ie. shift out from lookahead buffer) bits occupied by the
			 * code.
			 */
			v <<= k;
			w -= k;
		}

		return new Dane(db, 0, n);
	}

	public Collection<Parametr> parametry()
	{
		// nie akceptujemy parametrów póki co
		return Collections.emptySet();
	}
}
