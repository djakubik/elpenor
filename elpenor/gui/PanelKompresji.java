/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.gui;

import elpenor.szkielet.Kodek;
import elpenor.szkielet.KodekSparametryzowany;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
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
 * Panel, który buduje interfejs i umożliwia zainicjowanie kompresji
 * @author Darian Jakubik
 */
public class PanelKompresji extends PanelElpenor {

    private final PrzyciskO przyciskWLewo;
    private final PrzyciskO przyciskWPrawo;
    private final PrzyciskO przyciskWDol;
    private final PrzyciskO przyciskWGore;
    private ListaModulow listaWybranych;
    /**
     * Lista map zawierających parametry do kompresji
     */
    private ArrayList<Map<String, Integer>> mapy = new ArrayList<Map<String, Integer>>();

    /**
     * Ustawia parametry początkowe, dodaje i pozycjonuje główne elementy okna oraz ustawia nasłuchiwacze zdarzeń
     * @param okno Wskaźnik na główne okno programu
     */
    PanelKompresji(final JFrame okno)
    {

        super(okno);

        /*
         * Tworzenie górnego panelu
         */

        JPanel gornyPanel = new JPanel();
        gornyPanel.setLayout(new GridBagLayout());

        listaDostepnych = new ListaModulow("Dostępne moduły");

        listaWybranych = new ListaModulow("Wykorzystane moduły");

        String napisPanelInfo = "Ogólne informacje o modułach, które będą wyświetlone po wybraniu.";
        panelInfo = new PanelInfo("Informacje o module", new PrzyciskX("Konfiguruj", "konfiguruj_parametry", "Otwiera okno konfiguracyjne wybranego parametru"), napisPanelInfo);


        JPanel panelPrzyciskow = new JPanel();
        panelPrzyciskow.setLayout(new GridBagLayout());

        przyciskWLewo = new PrzyciskO("Usuń", "l", "przenieś_w_lewo", "Kliknij, aby usunąć moduł z listy", true);

        przyciskWPrawo = new PrzyciskO("Dodaj", "p", "przenieś_w_prawo", "Kliknij, aby dodać moduł do listy");


        JPanel panelBocznyPrzyciskow = new JPanel();
        panelBocznyPrzyciskow.setLayout(new GridBagLayout());

        przyciskWGore = new PrzyciskO("g", "przenieś_w_górę", "Przesuwa zaznaczony moduł jedną pozycję wyżej.");

        przyciskWDol = new PrzyciskO("d", "przenieś_w_dół", "Przesuwa zaznaczony moduł jedną pozycję niżej.");

        szczegolyModulow = new PrzyciskX("Szczegołowe informacje", "wyświetl_szczegółowe_informacje", "Otwiera okno z informacjami o każdym użytym module");

        przyciskWPrawo.setEnabled(false);
        przyciskWLewo.setEnabled(false);
        przyciskWGore.setEnabled(false);
        przyciskWDol.setEnabled(false);
        /*
         * Pozycjonowanie górnego panelu
         */

        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 10;

        gornyPanel.add(listaDostepnych, gbc);

        gbc.gridx = 10;
        //gbc.gridwidth = 14;
        gbc.gridheight = 1;

        gornyPanel.add(panelInfo, gbc);

        gbc.gridx = 24;
        //gbc.gridwidth = 10;
        gbc.gridheight = 2;

        gornyPanel.add(listaWybranych, gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        panelPrzyciskow.add(przyciskWLewo, gbc);

        gbc.gridx = 1;

        panelPrzyciskow.add(przyciskWPrawo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        panelPrzyciskow.add(szczegolyModulow, gbc);

        gbc.gridy = 1;
        gbc.gridx = 10;
        gbc.gridwidth = 14;

        gornyPanel.add(panelPrzyciskow, gbc);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        panelBocznyPrzyciskow.add(przyciskWGore, gbc);

        gbc.gridy = 1;

        panelBocznyPrzyciskow.add(przyciskWDol, gbc);

        gbc.gridy = 0;
        gbc.gridx = 20;
        gbc.gridheight = 1;
        gbc.gridwidth = 4;
        gbc.fill = gbc.NONE;
        gbc.weightx = 0;

        gornyPanel.add(panelBocznyPrzyciskow, gbc);

        gbc.fill = gbc.BOTH;
        gbc.weightx = 1;

        /*
         * Tworzenie dolnego panelu
         */

        Kontener dolnyPanel = new Kontener(true);


        Kontener gkontener = new Kontener(false);

        Kontener skontener = new Kontener(false);

        Kontener dkontener = new Kontener(false);


        JLabel opcja1 = new JLabel("Plik do kompresji", SwingConstants.CENTER);

        polePliku1 = new JTextField("", 40);

        wybor1 = new PrzyciskX("Wybierz", "wybierz_plik_wejściowy", "Kliknij, aby wybrać plik do kompresji");



        JLabel opcja2 = new JLabel("Plik po kompresji", SwingConstants.CENTER);

        polePliku2 = new JTextField("", 40);

        wybor2 = new PrzyciskX("Wybierz", "wybierz_plik_wyjściowy", "Kliknij, aby wybrać plik wyjściowy");


        dzialaj = new PrzyciskX("Kompresuj", "wykonaj_kompresję", "Zaczyna kompresję z wybranymi opcjami");
        aktualizujDzialaj();

        Kontener dLPanel = new Kontener(true);

        dLPanel.dodaj(new JPanel(), 2, gbc.CENTER);
        /*
        dLPanel.dodaj(new JLabel("Wybierz motyw"), 1,gbc.CENTER);
        dLPanel.dodaj(comboMotywow,1);*/


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
        dkontener.dodaj(dzialaj, 10);
        dkontener.dodaj(dPPanel, 1);

        dolnyPanel.dodaj(gkontener, 1);
        dolnyPanel.dodaj(skontener, 1);
        dolnyPanel.dodaj(dkontener, 4);

        //dolnyPanel.dodaj(dzialaj, 4,gbc.VERTICAL);

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
         * Ustawianie parametrów
         */

        ustawKodeki(kodeki, listaDostepnych);

        /*
         * Koniec ustawień parametrów
         * Metody okna
         */


        /*
         * Koniec metod
         * Obsługa zdarzeń
         */

        listaWybranych.lista.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt)
            {
                if (evt.getValueIsAdjusting() == false) {

                    int wybrany = listaWybranych.lista.getSelectedIndex();
                    if (wybrany == -1) {
                        przyciskWLewo.setEnabled(false);
                        przyciskWGore.setEnabled(false);
                        przyciskWDol.setEnabled(false);

                    } else {

                        int iloscModulow = listaWybranych.lista.getModel().getSize() - 1;
                        Kodek m = listaWybranych.listaModulow.get(wybrany);

                        przyciskWLewo.setEnabled(true);
                        przyciskWGore.setEnabled(wybrany > 0);
                        przyciskWDol.setEnabled(wybrany != iloscModulow);

                        panelInfo.ustawInfo(listaWybranych.listaModulow.get(wybrany).opis());
                        //Coś zaznaczone - włącz przycisk - wyslij info
                    }
                }
            }
        });

        listaDostepnych.lista.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt)
            {
                if (evt.getValueIsAdjusting() == false) {
                    int wybrany = listaDostepnych.lista.getSelectedIndex();
                    if (wybrany == -1) {
                        przyciskWPrawo.setEnabled(false);

                    } else {
                        przyciskWPrawo.setEnabled(true);

                        panelInfo.ustawInfo(listaDostepnych.listaModulow.get(wybrany).opis());
                        //Coś zaznaczone - włącz przycisk - wyslij info
                    }
                }
            }
        });

        przyciskWLewo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {
                int numer = listaWybranych.lista.getSelectedIndex();
                if (numer >= 0) {
                    listaWybranych.usunModul(numer);
                    mapy.remove(numer);

                    int wszystkie = listaWybranych.lista.getModel().getSize();
                    if (wszystkie > 0) {
                        if (numer == wszystkie) {
                            listaWybranych.lista.setSelectedIndex(numer - 1);

                        } else {
                            listaWybranych.lista.setSelectedIndex(numer);

                        }
                    }
                }

                aktualizujDzialaj();
            }
        });

        przyciskWPrawo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {
                int numer = listaDostepnych.lista.getSelectedIndex();
                if (numer >= 0) {
                    listaWybranych.dodajModul(listaDostepnych.listaModulow.get(numer));
                    listaWybranych.lista.setSelectedIndex(listaWybranych.lista.getModel().getSize() - 1);
                    mapy.add(new TreeMap<String, Integer>());

                }

                aktualizujDzialaj();
            }
        });

        przyciskWGore.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {
                int wybrany = listaWybranych.lista.getSelectedIndex();
                listaWybranych.zamienModul(wybrany, true);
                zamienElementyMapy(wybrany, true);
                listaWybranych.lista.setSelectedIndex(wybrany - 1);
            }
        });

        przyciskWDol.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {
                int wybrany = listaWybranych.lista.getSelectedIndex();
                listaWybranych.zamienModul(wybrany, false);
                zamienElementyMapy(wybrany, false);
                listaWybranych.lista.setSelectedIndex(wybrany + 1);
            }
        });

        wybor1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {

                JFileChooser ladowaczPlikow = new JFileChooser();

                int r = ladowaczPlikow.showOpenDialog(PanelKompresji.this);

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

                int r = zapisywaczPlikow.showSaveDialog(PanelKompresji.this);

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

                Collection<KodekSparametryzowany> doKompresji = new Vector<KodekSparametryzowany>();
                int iloscModulow = listaWybranych.listaModulow.size();

                for (int nrModulu = 0; nrModulu < iloscModulow; nrModulu++) {
                    Kodek szukany = listaWybranych.listaModulow.get(nrModulu);
                    String nazwaModulu = szukany.nazwa();

                    for (Kodek kodek : kodeki) {
                        if (kodek.nazwa().equals(nazwaModulu)) {

                            doKompresji.add(new KodekSparametryzowany(kodek, mapy.get(nrModulu)));

                            break;
                        }
                    }
                    ;
                }

                if (doKompresji.isEmpty()) {
                    System.err.println("Wybierz poprawnie moduły!");
                } else {
                    try {

                        String path1 = polePliku1.getText();
                        String path2 = polePliku2.getText();

                        System.out.println(path1);
                        System.out.println(path2);

                        if (engine.kompresuj(path1, path2, doKompresji)) {
                            new KomunikatKońcowy(okno, true, true, true);
                        } else {
                            new KomunikatKońcowy(okno, true, true, false);
                        }
                    } catch (IOException exc) {
                        // TODO Auto-generated catch block
                        exc.printStackTrace();
                    }
                }

            }
        });

        szczegolyModulow.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {
                OknoSzczegolow oknos = new OknoSzczegolow(okno, kodeki);
            }
        });

        panelInfo.podajPrzycisk().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {
                int wybrany = listaWybranych.lista.getSelectedIndex();
                if (wybrany >= 0) {
                    //new OknoParametrow(okno, listaWybranych.listaModulow.get(wybrany), mapy.get(wybrany));

                    OknoParametrow ok = new OknoParametrow(okno, true);
                    ok.ustawienia(listaWybranych.listaModulow.get(wybrany), mapy.get(wybrany));
                }
            }
        });
    }

    /**
     * aktualizuje dostępność przycisku (de)kompresuj
     */
    private void aktualizujDzialaj()
    {
        dzialaj.setEnabled(!polePliku1.getText().isEmpty() && !polePliku2.getText().isEmpty() && listaWybranych.lista.getModel().getSize() != 0);
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
     * Zamienia wartości dwóch map koło siebie
     * @param nr Indeks elementu z listy do zmiany
     * @param wGore Określa, w którą stronę postępuje zmiana
     */
    public void zamienElementyMapy(int nr, boolean wGore)
    {
        int nrz = wGore ? nr - 1 : nr + 1;

        Map<String, Integer> temp = mapy.get(nr);
        mapy.set(nr, mapy.get(nrz));
        mapy.set(nrz, temp);
    }
}
