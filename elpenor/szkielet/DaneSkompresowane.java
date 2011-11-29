package elpenor.szkielet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class DaneSkompresowane
{
	private final FileInputStream fin;
	private final List<Kodek> ks;
	private final Metadane md;
	private final int rozm_we;
	private final int rozm_wy;
	
	public DaneSkompresowane(FileInputStream fin, List<Kodek> ks, Metadane md, int rozm_we, int rozm_wy)
	{
		this.fin = fin;
		this.ks = ks;
		this.md = md;
		this.rozm_we = rozm_we;
		this.rozm_wy = rozm_wy;
	}

	public boolean dekompresuj(String plik_wy)
			throws FileNotFoundException
	{
		FileOutputStream fout = new FileOutputStream(plik_wy);
		StosKompresji sk = new StosKompresji();
		return sk.dekompresuj(fin, fout, ks, md);
	}
	
        public List<Kodek> podajKodeki()
        {
		return ks;
        }

        public Metadane podajMetadane()
        {
		return md;
        }

	public int podajRozmiarWejsciowy()
	{
		return rozm_we;
	}

	public int podajRozmiarWyjsciowy()
	{
		return rozm_wy;
	}
}
