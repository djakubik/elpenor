/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elpenor.gui;

/**
 * Klasa przycisku zawierającego tylko tekst
 * @author Darian Jakubik
 */
public class PrzyciskX extends Przycisk {

    /**
     * Wywołuje konstruktor Przycisku ustawiając tekst oraz ustawia informacje o rozkazie wykonywanego zdarzenia oraz tekst "ToolTip"
     * @param text Napis wyświetlający się w przycisku
     * @param reakcja Rozkaz wykonywanego zdarzenia przez przycisk
     * @param pomoc Tekst pomocniczy wyświetlany jako "ToolTip"
     */
    PrzyciskX(String text,String reakcja, String pomoc)
    {
        super(text);

        setActionCommand(reakcja);
        setToolTipText(pomoc);
    }
}
