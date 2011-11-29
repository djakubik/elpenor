package elpenor.szkielet;

import java.util.Map;
import java.util.TreeMap;

/**
 * Klasa ta definiuje kodek z określonymi parametrami konfiguracyjnymi
 * kompresji.
 * 
 * @author Mikołaj Izdebski
 */
public final class KodekSparametryzowany
{
	private final Kodek kodek;
	private final Map<String, Integer> parametry;

	/**
	 * Tworzy nowy kodek sparametryzowany parametrami domyślnymi.
	 * 
	 * @param kodek
	 *          kodek
	 */
	public KodekSparametryzowany(Kodek kodek)
	{
		this(kodek, new TreeMap<String, Integer>());
	}

	/**
	 * Tworzy nowy kodek sparametryzowany danymi parametrami.
	 * 
	 * @param kodek
	 *          kodek
	 * @param parametry
	 *          parametry kodeku
	 */
	public KodekSparametryzowany(Kodek kodek, Map<String, Integer> parametry)
	{
		this.kodek = kodek;
		this.parametry = parametry;
	}

	/**
	 * Zwraca kodek.
	 * 
	 * @return kodek
	 */
	public Kodek kodek()
	{
		return kodek;
	}

	/**
	 * Zwraca parametry kodeku.
	 * 
	 * @return parametry kodeku
	 */
	public Map<String, Integer> parametry()
	{
		return parametry;
	}
}
