package elpenor.szkielet;

import java.util.concurrent.Semaphore;

/**
 * Bufor jest klasą generyczą mająca na celu rozwiązanie problemu synchronizacji
 * producenta i konsumenta. Producent produkuje dane i dodaje je do bufora za
 * pomocą metody dodaj(). Konsument pobiera wyprodukowane dane za pomoca metody
 * pobierz().
 * 
 * <p>Bufor jest zaimplementowany jako statyczna cykliczna kolejka FIFO osłaniana
 * przez dwa semafory.
 * 
 * @author Mikołaj Izdebski
 */
@SuppressWarnings("unchecked")
public class Bufor<T>
{
	private static final int ROZMIAR_BUFORA = 16;
	private final Semaphore pelne = new Semaphore(0);
	private final Semaphore puste = new Semaphore(ROZMIAR_BUFORA);
	private T[] buf = (T[]) new Object[ROZMIAR_BUFORA];
	private int glowa = 0;
	private int ogon = 0;

	/**
	 * Dodaje jeden element do bufora. W przypadku, gdy bufor jest pełny ta
	 * operacja powoduje zablokowanie wykonania bierzącego wątku do czasu
	 * zwolnienia miejsca w buforze.
	 * 
	 * UWAGA: Ta metoda nie jest synchronizowana. Próba jednoczesnego dodania
	 * daych przez wiele wątków jednocześnie poboduje zachowanie nieokreślone.
	 * 
	 * @param t
	 *          element który ma zostać dodany
	 * @throws InterruptedException
	 *           jeżeli bierzący wątek został przerwany podczas oczekiwania na
	 *           zwolnienie miejsca w buforze
	 */
	public void dodaj(T t) throws InterruptedException
	{
		puste.acquire();
		buf[ogon] = t;
		ogon = (ogon + 1) % ROZMIAR_BUFORA;
		pelne.release();
	}

	/**
	 * Pobiera jeden element z bufora. W prypadku gdy bufor jest pusy ta operacja
	 * powoduje zablokowanie wykonywania bierzącego watku do czasu pojawienia się
	 * elementu w buforze.
	 * 
	 * UWAGA: Ta metoda nie jest synchronizowana. Próba jednoczesnego pobrania
	 * daych przez wiele wątków jednocześnie poboduje zachowanie nieokreślone.
	 * 
	 * @return element, który został pobrany z bufora
	 * @throws InterruptedException
	 *           jeżeli bierzący wątek został przerwany podczas oczekiwania na
	 *           pojawienie się elementu w buforze
	 */
	public T pobierz() throws InterruptedException
	{
		pelne.acquire();
		T t = buf[glowa];
		glowa = (glowa + 1) % ROZMIAR_BUFORA;
		puste.release();
		return t;
	}
}
