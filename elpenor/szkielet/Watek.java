package elpenor.szkielet;

/**
 * Abstrakcyjna klasa wątku. Wszystkie watki tworzone przez silnik Elpenora
 * dziedziczą po tej klasie. Każdy watek związany jest ze stosem kompresji,
 * który jest informowany o każdym zakończeniu wątku. Informacja ta polega na
 * wywołaniu odpowiedniej metody stosu kompresji.
 * 
 * @author Mikołaj Izdebski
 */
public abstract class Watek extends Thread
{
	private final StosKompresji sk;

	/**
	 * Tworzy nowy watek.
	 * 
	 * @param sk
	 *          stos komporesji który ma być powiadomiony o zakończeniu
	 *          wykonywania wątku
	 */
	public Watek(StosKompresji sk)
	{
		this.sk = sk;
	}

	public final void run()
	{
		try
		{
			robota();
			sk.watek_zakonczony(true);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			sk.watek_zakonczony(false);
		}
	}

	abstract void robota() throws Throwable;
}
