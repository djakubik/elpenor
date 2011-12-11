/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.gui;

import elpenor.szkielet.DaneSkompresowane;
import elpenor.szkielet.Kodek;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Panel, który buduje interfejs i umożliwia zainicjowanie dekompresji
 * @author Darian Jakubik
 */
public class PanelDekompresji extends PanelElpenor {

    private DaneSkompresowane ds;
    PrzyciskX wybierzPlik;
    PanelOPliku szczegolyPliku;
    private List<Kodek> kodeki = new ArrayList<Kodek>();
    private ArrayList<Map<String, Integer>> parametry = new ArrayList<Map<String, Integer>>();

    /**
     * Ustawia parametry początkowe, dodaje i pozycjonuje główne elementy okna oraz ustawia nasłuchiwacze zdarzeń
     * @param okno Wskaźnik na główne okno programu
     */
    PanelDekompresji(final JFrame okno)
    {

        super(okno);

        /*
         * Tworzenie górnego panelu
         */

        JPanel gornyPanel = new JPanel();
        gornyPanel.setLayout(new GridBagLayout());

        listaDostepnych = new ListaModulow("Użyte moduły");

        panelInfo = new PanelInfo("Informacje o module", null, "Tu będą wyświetlane informacje ogólne o module użytym w kompresji");

        JPanel panelPrzyciskow = new JPanel();
        panelPrzyciskow.setLayout(new GridBagLayout());

        szczegolyModulow = new PrzyciskX("Szczegołowe informacje", "wyświetl_szczegółowe_informacje", "Otwiera okno ze szczegółowymi informacjami kompresji każdym modułem");

        szczegolyPliku = new PanelOPliku();
        szczegolyPliku.wyczyscDane();


        /*
         * Pozycjonowanie górnego panelu
         */

        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 10;

        gornyPanel.add(listaDostepnych, gbc);

        gbc.gridx = 10;
        gbc.gridwidth = 14;
        gbc.gridheight = 1;

        gornyPanel.add(panelInfo, gbc);

        gbc.gridx = 24;
        gbc.gridwidth = 10;
        gbc.gridheight = 2;

        GridBagConstraints d = new GridBagConstraints();
        d = (GridBagConstraints) gbc.clone();

        gbc.fill = gbc.NONE;

        gornyPanel.add(szczegolyPliku, gbc);
        gbc = d;

        gbc.gridy = 1;
        gbc.gridx = 10;
        gbc.gridwidth = 14;
        gbc.gridheight = 1;

        gornyPanel.add(szczegolyModulow, gbc);

        /*
         * Tworzenie dolnego panelu
         */

        Kontener dolnyPanel = new Kontener(true);


        Kontener gkontener = new Kontener(false);

        Kontener skontener = new Kontener(false);

        Kontener dkontener = new Kontener(false);


        JLabel opcja1 = new JLabel("Plik do dekompresji", SwingConstants.CENTER);

        polePliku1 = new JTextField("", 40);

        wybor1 = new PrzyciskX("Wybierz", "wybierz_plik_wejściowy", "Kliknij, aby wybrać plik do dekompresji");



        JLabel opcja2 = new JLabel("Plik po dekompresji", SwingConstants.CENTER);

        polePliku2 = new JTextField("", 40);

        wybor2 = new PrzyciskX("Wybierz", "wybierz_plik_wyjściowy", "Kliknij, aby wybrać plik wyjściowy");


        dzialaj = new PrzyciskX("Dekompresuj", "wykonaj_dekompresję", "Wykonuje dekompresję wybranego pliku");
        wybierzPlik = new PrzyciskX("Wczytaj dane", "wybierz_plik", "Wczytuje metadane pliku i przygotowuje do dekompresji");
        aktualizujDzialaj();

        Kontener dLPanel = new Kontener(true);

        dLPanel.dodaj(wybierzPlik, 2);

        /*
         * Pozycjonowanie dolnego panelu
         */

        gkontener.dodaj(opcja1, 3);
        gkontener.dodaj(polePliku1, 10, gbc.HORIZONTAL);
        gkontener.dodaj(wybor1, 2, gbc.HORIZONTAL);

        skontener.dodaj(opcja2, 3);
        skontener.dodaj(polePliku2, 10, gbc.HORIZONTAL);
        skontener.dodaj(wybor2, 2, gbc.HORIZONTAL);

        dkontener.dodaj(dLPanel, 1);
        dkontener.dodaj(dzialaj, 5);
        dkontener.dodaj(dPPanel, 1);

        dolnyPanel.dodaj(gkontener, 1);
        dolnyPanel.dodaj(skontener, 1);
        dolnyPanel.dodaj(dkontener, 4);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 5;

        this.add(gornyPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 1;

        this.add(dolnyPanel, gbc);


        /*
         * Koniec ustawień okna
         * Obsługa zdarzeń
         */


        listaDostepnych.lista.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt)
            {
                if (evt.getValueIsAdjusting() == false) {
                    int wybrany = listaDostepnych.lista.getSelectedIndex();
                    if (wybrany != -1) {
                        panelInfo.ustawInfo(listaDostepnych.listaModulow.get(wybrany).opis());

                    }
                }
            }
        });

        wybor1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {

                JFileChooser ladowaczPlikow = new JFileChooser();

                int r = ladowaczPlikow.showDialog(PanelDekompresji.this, "Wybierz");

                if (r == JFileChooser.APPROVE_OPTION) {
                    File s = ladowaczPlikow.getSelectedFile();
                    s.getPath();
                    polePliku1.setText(s.getPath());
                }
            }
        });

        wybor2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {

                JFileChooser zapisywaczPlikow = new JFileChooser();

                int r = zapisywaczPlikow.showDialog(PanelDekompresji.this, "Wybierz");

                if (r == JFileChooser.APPROVE_OPTION) {
                    File s = zapisywaczPlikow.getSelectedFile();
                    s.getPath();
                    polePliku2.setText(s.getPath());
                }
            }
        });



        DocumentListener polePlikuAction = new DocumentListener() {

            public void insertUpdate(DocumentEvent de)
            {
                aktualizujDzialaj();
            }

            public void removeUpdate(DocumentEvent de)
            {
                aktualizujDzialaj();
            }

            public void changedUpdate(DocumentEvent de)
            {
                aktualizujDzialaj();
            }
        };

        polePliku1.getDocument().addDocumentListener(polePlikuAction);
        polePliku2.getDocument().addDocumentListener(polePlikuAction);


        dzialaj.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {

                if (ds != null) {

                    String path2 = polePliku2.getText();

                    System.out.println(path2);
                    try {
                        if (ds.dekompresuj(path2)) {
                            new KomunikatKońcowy(okno, true, false, true);
                        } else {
                            new KomunikatKońcowy(okno, true, false, false);
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PanelDekompresji.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                aktualizujDzialaj();
            }
        });

        wybierzPlik.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {
                try {
                    ds = engine.wczytaj_dane_skompresowane(polePliku1.getText());

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PanelDekompresji.class.getName()).log(Level.SEVERE, null, ex);
                }

                wyczyscKodeki(listaDostepnych);
                parametry.clear();
                aktualizujDzialaj();

                if (ds != null) {

                    kodeki = ds.podajKodeki();

                    ustawKodeki(kodeki, listaDostepnych);

                    int n = polePliku1.getText().lastIndexOf('/');
                    String nazwa = polePliku1.getText().substring(++n);
                    szczegolyPliku.wstawDane(nazwa, ds.podajRozmiarWejsciowy(), ds.podajRozmiarWyjsciowy());

                } else {
                    szczegolyPliku.wyczyscDane();
                }
            }
        });

        szczegolyModulow.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {
                OknoSzczegolow oknoS = new OknoSzczegolow(okno, kodeki);
            }
        });


    }

    /**
     * Aktualizuje dostępność przycisku (de)kompresuj
     */
    private void aktualizujDzialaj()
    {
        wybierzPlik.setEnabled(!polePliku1.getText().isEmpty());
        dzialaj.setEnabled(ds != null && !polePliku2.getText().isEmpty());
    }

    /**
     * Dodaje daną kolekcję kodeków do listy modułów
     * @param kodeki kolekcja kodeków do wrzucenia na listę
     * @param lista lista, która ma być uzupełniona
     */
    private void ustawKodeki(Collection<Kodek> kodeki, ListaModulow lista)
    {
        for (Kodek kodek : kodeki) {
            kodek.id();
            lista.dodajModul(kodek);

        }
    }

    /**
     * Czyści listę kodeków użytych do kompresji
     * @param lista lista, która ma być wyczyszczona
     */
    private void wyczyscKodeki(ListaModulow lista)
    {
        lista.usunWszystkieModuly();
    }
}
