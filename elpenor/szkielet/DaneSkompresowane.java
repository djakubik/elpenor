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
	
	public DaneSkompresowane(FileInputStream fin, List<Kodek> ks, Metadane md)
	{
		this.fin = fin;
		this.ks = ks;
		this.md = md;
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
}
