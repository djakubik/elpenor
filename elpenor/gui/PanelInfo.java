/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel wyświetlający dane jako HTML i mogący posiadać przycisk
 * @author Darian Jakubik
 */


public class PanelInfo extends JPanel
{

    private Przycisk przycisk;
    private JLabel info;

    /**
     * Tworzy obramowanie z tytułem, wyświetla tekst w znaczniku HTML oraz tworzy przycisk
     * @param tytul Tytuł panelu
     * @param przycisk Przycisk, który ma być dodany do panelu
     * @param informacje Opis, który ma znajdować się w środku ramki
     */
    PanelInfo(String tytul, Przycisk przycisk, String informacje)
    {

        super();

        this.setBorder(
                BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(tytul),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        info = new JLabel("<HTML><p><span style=\"text-align:right;\">" + informacje.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")+"</span></p>");
        
        if (przycisk != null)
        {
            this.przycisk = przycisk;
            this.przycisk.setEnabled(true);
        } 

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.gridwidth = 1;
        gbc.gridheight = 5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = gbc.BOTH;

        add(info, gbc);

        gbc.fill = gbc.REMAINDER;
        gbc.weighty = 1;
        gbc.gridy = 6;
        gbc.gridheight = 1;

        if (this.przycisk != null)
        {
            add(this.przycisk, gbc);
        }

    }

    /**
     * Ustawia w ramce napis
     * @param informacje Napis do wyświetlenia
     */
    public void ustawInfo(String informacje)
    {
        if (informacje != null)
        {
            this.info.setText("<HTML>" + informacje.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;"));
        }
    }

    /**
     * Włącza lub wyłącza przycisk w ramce
     * @param w Określa czy przycisk ma być włączony czy nie
     */
    public void ustawPrzycisk(boolean w)
    {
        przycisk.setEnabled(w);
    }

    /**
     * Metoda zwracająca wskaźnik na przycisk znajdujący się w panelu
     * @return Zwraca wskaźnik na przycisk znajdujący się w panelu
     */
    public Przycisk podajPrzycisk()
    {
        return przycisk;
    }
}
