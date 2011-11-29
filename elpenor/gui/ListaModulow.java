/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.gui;

import elpenor.szkielet.Kodek;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

/**
 * Lista zawierająca moduły służące do kompresji
 * @author Darian Jakubik
 */
public class ListaModulow extends JPanel {

    /**
     * Lista przedstawiająca graficznie tablicę kodeków
     */
    JList lista;
    private DefaultListModel modelListy = new DefaultListModel();
    /**
     * Tablica kodeków w liście
     */
    ArrayList<Kodek> listaModulow = new ArrayList<Kodek>();

    /**
     * Właściwy konstruktor. Rysuje obramowanie, ustawia parametry pozycjonowania i tworzy listę właściwą
     * @param nazwaListy Nazwa całej listy widoczna w obramowaniu
     */
    ListaModulow(String nazwaListy) {

        this.setBorder(
                BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(nazwaListy),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = gbc.BOTH;

        lista = new JList(modelListy);
        JScrollPane skrol = new JScrollPane(lista);

        this.add(skrol, gbc);


    }

    /**
     * Dodaje moduł do listy potem jej nie aktualizując
     * @param kodek Moduł dodawany do listy
     */
    public void dodajModul(Kodek kodek) {
        listaModulow.add(kodek);
        modelListy.addElement(kodek.nazwa());

    }

    /**
     * Usuwa moduł z listy
     * @param nr Indeks modułu do usunięcia
     */
    public void usunModul(int nr) {
        listaModulow.remove(nr);
        modelListy.removeElementAt(nr);
    }

    public void usunWszystkieModuly() {
        int nr = this.listaModulow.size();
        while (nr > 0) {
            nr--;
            listaModulow.remove(nr);
            modelListy.removeElementAt(nr);
        }
    }

    /**
     * Zamienia pozycjami sąsiednie moduły
     * @param nr Indeks zamienianego modułu
     * @param wGore Określa czy dany moduł ma iść jedną pozycję w górę czy w dół
     */
    public void zamienModul(int nr, boolean wGore) {
        int nrz = wGore ? nr - 1 : nr + 1;

        Kodek temp = listaModulow.get(nr);
        listaModulow.set(nr, listaModulow.get(nrz));
        listaModulow.set(nrz, temp);

        aktualizujListe();
    }

    /**
     * Aktualizuje listę modułów
     */
    private void aktualizujListe() {

        modelListy.clear();
        int j = listaModulow.size();

        for (int i = 0; i < j; i++) {

            modelListy.addElement(listaModulow.get(i).nazwa());

        }
    }
}
