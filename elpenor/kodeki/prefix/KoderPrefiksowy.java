package elpenor.kodeki.prefix;

import java.util.Arrays;

/**
 * Koder prefiksowy
 * 
 * Ta klasa implementuje algorytm "Package-Merge" znajdujący optymalny zestaw
 * słów kodowych o ograniczonej długości, opisany w [1].
 * 
 * @see [1] "A Fast Algorithm for Optimal Length-Limited Huffman Codes",
 *      Lawrence Larmore, Daniel Hirschberg,
 *      http://www.cs.unlv.edu/~larmore/Research/larHir90.pdf
 * 
 * @author Mikołaj Izdebski
 */
class KoderPrefiksowy
{
	final int[] len;
	final int[] code;

	/**
	 * Tworzy nowy koder prefiksowy.
	 * 
	 * @param P
	 *          wagi poszczególnych symboli
	 * @param n
	 *          liczba symboli
	 * @param m
	 *          maksymalna długość kodów
	 */
	KoderPrefiksowy(int[] P, int n, int m)
	{
		int i;
		int k;
		int d;
		int c = 0;
		long[] V = new long[n];
		int[] B = new int[n];
		int[] L = new int[n];

		/*
		 * Label weights with sequence numbers. Labelling has two main purposes:
		 * firstly it allows to sort pairs of weight and sequence number more
		 * easily; secondly: the package-merge algorithm requires strict
		 * monotonicity of weights and putting an uniquie value in lower bits
		 * provides that.
		 */
		for (i = 0; i < n; i++)
		{
			/*
			 * FFFFFFFF00000000 - symbol ferequencuy 00000000FF000000 - node depth
			 * 0000000000FF0000 - initially one 000000000000FFFF - symbol
			 */
			if (P[i] == 0)
				V[i] = (1L << 32) | 0x10000 | (n - i);
			else
				V[i] = ((long) P[i] << 32) | 0x10000 | (n - i);
		}

		/* Sort weights and sequence numbers toogether. */
		Arrays.sort(V);
		for (i = 0; i <= n / 2; i++)
		{
			long tt = V[i];
			V[i] = V[n - i - 1];
			V[n - i - 1] = tt;
		}

		int[] C = package_merge(V, n, m);

		{
			long S = 0;
			for (i = 1; i <= 20; i++)
				S += (long) C[i] << (20 - i);
			assert (S == 1L << 20);
		}

		/* Generate code lengths and transform counts into base codes. */
		i = 0;
		for (d = 0; d <= m; d++)
		{
			k = C[d];

			C[d] = c;
			c = ((c + k) << 1);

			while (k != 0)
			{
				assert (i < n);
				B[n - (int) (V[i] & 0xFFFF)] = d;
				i++;
				k--;
			}
		}

		assert (i == n);

		/* Assign prefix-free codes. */
		for (i = 0; i < n; i++)
			L[i] = C[B[i]]++;

		this.len = B;
		this.code = L;
	}

	/* O(n log(n)) */
	static int[] package_merge(long[] Pr, int n, int md)
	{
		PakietMonet[] arr1 = new PakietMonet[n];
		PakietMonet[] arr2 = new PakietMonet[n];
		PakietMonet t1;
		PakietMonet t2;

		int i;
		int d;

		int jP;
		int szP;
		int szL;
		PakietMonet[] P;
		PakietMonet[] L;
		PakietMonet[] T;

		/* S przechowuje rozwiązanie problemu. */
		PakietMonet S = new PakietMonet();

		P = arr1;
		L = arr2;
		szP = 0;

		for (d = md; d > 0; d--)
		{
			i = 0;
			jP = 0;
			szL = 0;

			while ((n - i) + (szP - jP) >= 2)
			{
				if (jP == szP || (i < n && Pr[n - 1 - i] < P[jP].waga()))
				{
					assert (i < n);
					t1 = new PakietMonet(Pr[n - 1 - i], d);
					i++;
				}
				else
				{
					assert (jP < szP);
					t1 = P[jP++];
				}

				if (jP == szP || (i < n && Pr[n - 1 - i] < P[jP].waga()))
				{
					assert (i < n);
					t2 = new PakietMonet(Pr[n - 1 - i], d);
					i++;
				}
				else
				{
					assert (jP < szP);
					t2 = P[jP++];
				}

				L[szL++] = new PakietMonet(t1, t2);
			}

			T = P;
			P = L;
			L = T;
			szP = szL;
			assert (szP > 0);
			assert (szP < n);
		}

		/*
		 * Drzewo binarne o n liściach (czyli n-1 węzłach wewnetrznych) kosztuje
		 * n-1.
		 */
		int X = n - 1;

		while (X > 0)
		{
			jP = 0;

			if ((X & 1) != 0)
			{
				S = new PakietMonet(S, P[jP]);
				jP++;
			}
			X >>= 1;

			szL = 0;
			while (szP - jP >= 2)
			{
				t1 = P[jP++];
				t2 = P[jP++];
				assert (szL < jP);
				P[szL++] = new PakietMonet(t1, t2);
			}

			szP = szL;
			assert ((X == 0) == (szP == 0));
			assert (szP < n);
		}

		return S.rozpakuj();
	}
}
