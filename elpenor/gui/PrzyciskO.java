/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

/**
 * Klasa przycisku zawierającego obrazek z tekstem
 * @author Darian Jakubik
 */
public class PrzyciskO extends Przycisk {

    private BufferedImage mojObrazek;
    private String motyw = "cs";

    /**
     * Wywołuje konstruktor ustawiając niektóre domyślne wartości
     * @param nazwaObrazka Nazwa Obrazka w folderze wybranego motywu
     * @param reakcja Rozkaz wykonywanego zdarzenia przez przycisk
     * @param pomoc Tekst pomocniczy wyświetlany jako "ToolTip"
     */
    PrzyciskO(String nazwaObrazka, String reakcja, String pomoc)
    {
        this(null, nazwaObrazka, reakcja, pomoc, false);
    }

    /**
     * Wywołuje konstruktor ustawiając niektóre domyślne wartości
     * @param tekstObrazka Napis wyświetlający się w przycisku
     * @param nazwaObrazka Nazwa Obrazka w folderze wybranego motywu
     * @param reakcja Rozkaz wykonywanego zdarzenia przez przycisk
     * @param pomoc Tekst pomocniczy wyświetlany jako "ToolTip"
     */
    PrzyciskO(String tekstObrazka, String nazwaObrazka, String reakcja, String pomoc)
    {
        this(tekstObrazka, nazwaObrazka, reakcja, pomoc, false);
    }

    /**
     * Właściwy konstruktor. Wywołuje konstruktor Przycisku ustawiając tekst, ustawia informacje o rozkazie wykonywanego zdarzenia, tekst "ToolTip" oraz wyświetla obrazek
     * @param tekstObrazka Napis wyświetlający się w przycisku
     * @param nazwaObrazka Nazwa Obrazka w folderze wybranego motywu
     * @param reakcja Rozkaz wykonywanego zdarzenia przez przycisk
     * @param pomoc Tekst pomocniczy wyświetlany jako "ToolTip"
     * @param right Informacja czy obrazek powinien być po prawej stronie tekstu
     */
    PrzyciskO(String tekstObrazka, String nazwaObrazka, String reakcja, String pomoc, boolean right) {
        
        super(tekstObrazka);

        try {
            try {
                nazwaObrazka = "motywy/" +motyw+ "/"+nazwaObrazka + ".png";
            mojObrazek = ImageIO.read(this.getClass().getResource(nazwaObrazka ));
            } catch (IllegalArgumentException e1 )
            {
                System.out.println("Błąd podczas ładowania obrazka: "+nazwaObrazka+" - nie istnieje");
                mojObrazek = null;
            }
        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania obrazka: "+nazwaObrazka+" - brak dostępu");
            mojObrazek = null;

        }

        if (mojObrazek != null) {

            ImageIcon mojaIkona = new ImageIcon(mojObrazek);
            setIcon(mojaIkona);
            
            
        } else {
            setText("E:"+nazwaObrazka);
        }


        if (right)
        {
            setHorizontalTextPosition(SwingConstants.RIGHT);
        }
        else {
            setHorizontalTextPosition(AbstractButton.LEADING);
        }

        setActionCommand(reakcja);
        setToolTipText(pomoc);
    }

}
