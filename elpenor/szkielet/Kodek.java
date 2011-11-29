package elpenor.szkielet;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.zip.DataFormatException;

/**
 * Abstrakcyjna klasa kodeku. Wszystkie kodeki dostępne w systemie dziedziczą po
 * tej klasie.
 * <p>
 * Podstawowymi operacjami udostepnianymi przez kodek są kodowanie i dekodowanie
 * denego bufora danych. Ponadto każdy kodek umożliwia pobranie jego danych
 * identyfikacyjnych (ID, nazwa, opis) jak również dostepnych parametrów
 * konfiguracyjnych modyfikujących domyślne zachowanie kodera.
 * 
 * @author Mikołaj Izdebski
 */
public abstract class Kodek
{
	public abstract Dane zakoduj(Dane b, Map<String, Integer> par);

	public abstract Dane odkoduj(Dane b, PrintWriter pw) throws DataFormatException;

	/**
	 * Zwraca nazwę danego kodeku.
	 * 
	 * @return nazwa kodeku
	 */
	public abstract String nazwa();

	/**
	 * Zwraca opis danego kodeku.
	 * 
	 * @return opis kodeku
	 */
	public abstract String opis();

	/**
	 * Zwraca unikalny identyfikator danego kodeku.
	 * 
	 * @return unikalny identyfikator kodeku
	 */
	public abstract int id();

	/**
	 * Zwraca kolekcję dostepnych parametrów konfiguracyjnych danego kodeku.
	 * <p>
	 * Domyślna implementacja zwraca pustą kolekcję parametrów, co oznacza
	 * nieakceptowanie żadnych parametrów przez kodek.
	 * 
	 * @return kolekcja dostepnych parametrów konfiguracyjnych kodeku
	 */
	public Collection<Parametr> parametry()
	{
		return Collections.emptySet();
	}

	/**
	 * Klasa <code>Parametr</code> definiuje pojedynczy parametr konfiguracujny
	 * kodeku. Parametry modyfikują domyslne zachowanie koderów.
	 * <p>
	 * Każdy parametr jest liczbą naturalną typu integer z określonego przedziału
	 * liczbowego.
	 * 
	 * @author Mikołaj Izdebski
	 */
	public final class Parametr
	{
		private final String nazwa;
		private final String opis;
		private final int min;
		private final int max;
		private final int domyslna;

		/**
		 * Tworzy nowy parametr kodeku.
		 * 
		 * @param nazwa
		 *          nazwa parametru
		 * @param opis
		 *          opis parametru
		 * @param min
		 *          minimalna dozwolona wartość parametru
		 * @param max
		 *          maksymalna dozwolona wartość parametru
		 * @param domyslna
		 *          domyślna wartość parametru
		 */
		public Parametr(String nazwa, String opis, int min, int max, int domyslna)
		{
			this.nazwa = nazwa;
			this.opis = opis;
			this.min = min;
			this.max = max;
			this.domyslna = domyslna;
		}

		/**
		 * Zwraca nazwę parametru.
		 * 
		 * @return nazwa parametru
		 */
		public String nazwa()
		{
			return nazwa;
		}

		/**
		 * Zwraca opis parametru.
		 * 
		 * @return opis parametru
		 */
		public String opis()
		{
			return opis;
		}

		/**
		 * Zwraca minimalną dozwolona wartość parametru.
		 * 
		 * @return minimalna dozwolona wartość parametru
		 */
		public int min()
		{
			return min;
		}

		/**
		 * Zwraca maksymalną dozwolona wartość parametru.
		 * 
		 * @return maksymalna dozwolona wartość parametru
		 */
		public int max()
		{
			return max;
		}

		/**
		 * Zwraca domyślną wartość parametru.
		 * 
		 * @return domyślna wartość parametru
		 */
		public int domyslna()
		{
			return domyslna;
		}
	}
}
