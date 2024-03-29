package elpenor.kodeki;

import java.io.PrintWriter;
import java.util.Map;

import elpenor.szkielet.Kodek;
import elpenor.szkielet.Dane;

/**
 * @author Mikołaj Izdebski
 */
public class GaderyPoluki extends Kodek
{
	private static final int ID = 3;

	public int id()
	{
		return ID;
	}

	public String nazwa()
	{
		return "GaderyPoluki";
	}

	public String opis()
	{
		return "Gaderypoluki – rodzaj prostego monoalfabetycznego "
                        + "szyfru podstawieniowego stosowanego w harcerstwie "
                        + "do szyfrowania krótkich wiadomości. Technicznie jest"
                        + " to prosty, symetryczny, monoalfabetyczny szyfr "
                        + "podstawieniowy, w którym szyfrowanie oparte jest na "
                        + "krótkim, łatwym do zapamiętania kluczu. Klucz ten "
                        + "zapisuje się w formie ciągu par liter, które ulegają"
                        + " w tym szyfrze prostemu zastąpieniu. Najczęściej "
                        + "stosowany klucz to \"GA-DE-RY-PO-LU-KI\", skąd "
                        + "pochodzi nazwa szyfru. W kluczu tym każda para liter"
                        + " oddzielonych myślnikiem stanowi listę zamienników. "
                        + "Litery, których nie ma liście zamienników, pozostawia"
                        + " się w szyfrowanym tekście bez zmian. Zmianom nie "
                        + "podlegają też spacje.";
	}

	/*
	 * Poniższa tabela została wygenerowana poleceniem uniksowym:
	 * 
	 * <code>perl -e
	 * 'for(0..255){$_=chr;y/GADERYPOLUKIgaderypoluki/AGEDYROPULIKagedyropulik/;print
	 * ord()<<24>>24,","}'</code>
	 */
	private static final int[] tabela = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
			30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47,
			48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 71,
			66, 67, 69, 68, 70, 65, 72, 75, 74, 73, 85, 77, 78, 80, 79, 81, 89, 83,
			84, 76, 86, 87, 88, 82, 90, 91, 92, 93, 94, 95, 96, 103, 98, 99, 101,
			100, 102, 97, 104, 107, 106, 105, 117, 109, 110, 112, 111, 113, 121,
			115, 116, 108, 118, 119, 120, 114, 122, 123, 124, 125, 126, 127, 128,
			129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142,
			143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156,
			157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170,
			171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184,
			185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198,
			199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212,
			213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226,
			227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240,
			241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254,
			255, };

	public Dane zakoduj(Dane b, Map<String, Integer> par)
	{
		final byte[] buf = b.bufor();
		final int lim = b.offset() + b.length();
		for (int off = b.offset(); off < lim; off++)
			buf[off] = (byte) tabela[buf[off] & 0xFF];
		return b;
	}

	public Dane odkoduj(Dane b, PrintWriter pw)
	{
		return zakoduj(b, null);
	}
}
