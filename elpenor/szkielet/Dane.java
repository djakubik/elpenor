package elpenor.szkielet;

/**
 * @author Mikołaj Izdebski
 */
public class Dane {
	private final byte[] bufor;
	private final int offset;
	private final int length;
	private final Metadane meta;

	public Dane(byte[] bufor, int offset, int length) {
		this.bufor = bufor;
		this.offset = offset;
		this.length = length;
		meta = new Metadane();
	}

	public byte[] bufor() {
		return bufor;
	}

	public int offset() {
		return offset;
	}

	public int length() {
		return length;
	}
	
	public Metadane meta()
	{
		return meta;
	}
	
	public void dodajMeta(Metadane meta)
	{
		this.meta.dodaj(meta);
	}
}
