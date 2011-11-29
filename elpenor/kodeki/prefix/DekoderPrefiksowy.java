package elpenor.kodeki.prefix;

import java.util.zip.DataFormatException;

class DekoderPrefiksowy
{
	static final int SW = 12;
	final long[] base = new long[22];
	final int[] count = new int[22];
	final int[] perm = new int[256];
	final int[] start = new int[1 << SW];

	DekoderPrefiksowy(int[] L, int n) throws DataFormatException
	{
		int[] C; /* code length count; C[0] is a sentinel (always zero) */
		long[] B; /* left-justified base */
		int[] P; /* symbols sorted in ascending code length order */
		int[] S; /* lookup table */

		int k; /* current code length */
		int s; /* current symbol */
		long sofar, next;
		int cum;
		int code;
		long inc;
		int x;

		long v;

		C = this.count;
		B = this.base;
		P = this.perm;
		S = this.start;

		/* Count symbol lengths. */
		for (k = 0; k <= 20; k++)
			C[k] = 0;
		for (s = 0; s < n; s++)
		{
			k = L[s];
			C[k]++;
		}
		
		{
			long S1 = 0;
			for (int i = 1; i <= 20; i++)
				S1 += (long) C[i] << (20 - i);
			assert (S1 == 1L << 20);
		}


		/*
		 * Create left-justified base table. At this point we also check for
		 * incomplete or oversubscribed codes.
		 */
		sofar = 0;
		for (k = 1; k <= 20; k++)
		{
			next = sofar + ((long) C[k] << (64 - k));
			if (next != 0 && ((next ^ sofar) > 0 && next < sofar || (next ^ sofar) < 0 && next > sofar))
				throw new DataFormatException("przepełnione drzewo prefiksowe");
			B[k] = sofar;
			sofar = next;
		}
		if (sofar != 0)
			throw new DataFormatException("niekompletne drzewo prefiksowe");

		/*
		 * The last few entries of lj-base may have overflowed to zero, so replace
		 * all trailing zeros with largest 64-bit value.
		 */
		assert (k == 21);
		k = 20;
		while (C[k] == 0)
		{
			assert (k > 1);
			assert (B[k] == 0);
			B[k--] = ~(long) 0;
		}

		/* Transform counts into cumulative counts. */
		cum = 0;
		for (k = 1; k <= 20; k++)
		{
			int t1 = C[k];
			C[k] = cum;
			cum += t1;
		}
		assert (cum == n);

		/* Perform counting sort. */
		P[C[L[0]]++] = 257;
		P[C[L[1]]++] = 258;
		for (s = 2; s < n - 1; s++)
			P[C[L[s]]++] = s - 1;
		P[C[L[n - 1]]++] = 0;

		/* Create first, complete start entries. */
		code = 0;
		inc = 1 << (SW - 1);
		for (k = 1; k <= SW; k++)
		{
			for (s = C[k - 1]; s < C[k]; s++)
			{
				x = (P[s] << 5) | k;
				v = code;
				code += inc;
				while (v < code)
					S[(int) v++] = x;
			}
			inc >>= 1;
		}

		/* Fill remaining, incomplete start entries. */
		assert (k == SW + 1);
		sofar = (long) code << (64 - SW);
		while (code < (1 << SW))
		{
			while (sofar >= B[k + 1])
				k++;
			S[code] = k;
			code++;
			sofar += (long) 1 << (64 - SW);
		}
		assert (sofar == 0);

		/*
		 * Restore cumulative counts as they were destroyed by the sorting phase.
		 * The sentinel wasn't touched, so no need to restore it.
		 */
		for (k = 20; k > 0; k--)
		{
			C[k] = C[k - 1];
		}

		/* Valid tables were created successfully. */
	}
}
