/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.gui;

import elpenor.szkielet.Kodek;
import elpenor.szkielet.Silnik;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Klasa nadrzędna paneli kompresji i dekompresji
 * @author Darian Jakubik
 */
public class PanelElpenor extends JPanel
{

    /**
     * Zmienna określająca sposób pozycjonowania elementów w siatce GridBagLayout
     */
    GridBagConstraints gbc;

    /**
     * Zmienna, która ma dostęp go głównego narzędzia kompresji i dekompresji
     */
    static Silnik engine = new Silnik();

    /**
     * Kolekcja kodeków udostępnionych przez główne narzędzie kompresji
     */
    static Collection<Kodek> kodeki = engine.kodeki();

    /**
     * Przycisk wybierający plik do kompresji/dekompresji
     */
    Przycisk wybor1;
    /**
     * Przycisk wybierający plik wyjściowy kompresji/dekompresji
     */
    Przycisk wybor2;
    /**
     * Pole zawierające nazwę pliku wejściowego
     */
    JTextField polePliku1;
    /**
     * Pole zawierające nazwę pliku wyjściowego
     */
    JTextField polePliku2;
    /**
     * Przycisk wyświetlający szczegóły kompresji
     */
    Przycisk szczegolyModulow;
    /**
     * Przycisk wydający polecenie kompresji/dekompresjis
     */
    Przycisk dzialaj;
    /**
     * Lista kodeków w panelu, jak i dostępnych jak i użytych w kompresji
     */
    ListaModulow listaDostepnych;
    /**
     * Panel zawierający informacje o danym module oraz umożliwia ich konfigurację
     */
    PanelInfo panelInfo;
    /**
     * Panel zawierający inf. o wersji i autorach
     */
    PanelInfo dPPanel;

    /**
     * Wskaźnik na okno nadrzędne
     */
    JFrame oknoG;
   


    /**
     * Konstruktor właściwy. Ustawia parametry początkowe okien wspólnych dla każdego panelu oraz parametry pozycjonowania okien
     * @param okno Wskaźnik na główne okno programu
     */
    PanelElpenor(JFrame okno)
    {

        this.oknoG = okno;

        dPPanel = new PanelInfo("Program", new PrzyciskX("O autorach", "wyświetl_autorów", "Wyświetla informacje o autorach"), "Wersja: 1.02");

        dPPanel.podajPrzycisk().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {
                OAutorach ok = new OAutorach(oknoG, true);
            }
        });



        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = gbc.BOTH;

    }

    /*@Override
    public void repaint()
    {
    System.out.println("s" + nrPanelu);
    }*/
}
