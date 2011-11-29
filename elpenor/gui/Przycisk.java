/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elpenor.gui;

import javax.swing.AbstractButton;
import javax.swing.JButton;

/**
 * Klasa nadrzędna przycisków
 * @author Darian Jakubik
 */
public class Przycisk extends JButton {

    /**
     * Wywołuje konstruktor klasy nadrzędnej ustawiając napis w przycisku oraz formatuje napis
     * @param text Napis do wyświetlenia w przycisku
     */
    Przycisk(String text)
    {
        super(text);

        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.LEADING);
    }
}
