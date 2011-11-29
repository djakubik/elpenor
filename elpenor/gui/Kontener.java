/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *Prosty w obsłudze panel. Posiada metody znacznie ułatwiające pozycjonowanie
 * i dodawanie elementów.
 *
 * @author Mikołaj Izdebski
 * @author Darian Jakubik
 */
public class Kontener extends JPanel
{

    private GridBagConstraints gbc = new GridBagConstraints();
    private final boolean pion;

    /**
     * Ustawia rodzaj wyglądu okna oraz wartość początkową zmiennej używanej do pozycjonowania elementów.
     * @param pion określa czy obiekty mają być dodawane pionowo, czy poziomo.
     */
    public Kontener(boolean pion)
    {
        setLayout(new GridBagLayout());
        this.pion = pion;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = gbc.BOTH;

    }

    /**
     * Służy do dodawania nowych elementów z odpowiednią wartością wagi elementu
     * @param com Komponent, do którego dodawany jest element
     * @param waga Wartość wagi elementu w tym komponencie
     */
    public void dodaj(Component com, int waga)
    {

        if (pion)
        {
            gbc.gridheight = waga;
        } else
        {
            gbc.gridwidth = waga;
        }
        add(com, gbc);

        if (pion)
        {
            gbc.gridy += waga;

        } else
        {
            gbc.gridx += waga;
        }

    }

    /**
     * Służy do dodawania nowych elementów z odpowiednią wartością wagi elementu oraz z odpowiednim wypełnieniem
     * @param com Komponent, do którego dodawany jest element
     * @param waga Wartość wagi elementu w tym komponencie
     * @param wypelnienie Określa rodzaj wypełnienia elementu w komponencie
     */
    public void dodaj(Component com, int waga, int wypelnienie)
    {
        gbc.fill = wypelnienie;
        dodaj(com, waga);
        gbc.fill = gbc.BOTH;
    }
}
