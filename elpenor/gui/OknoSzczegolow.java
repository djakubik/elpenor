/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.gui;

import elpenor.szkielet.Kodek;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Collection;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * Okno szczegółowych informacji o modułach użytych podczas kompresji
 * @author darian
 */
public class OknoSzczegolow extends JDialog
{

    private JTabbedPane tabPanel;

    /**
     * Tworzy okno i wyświetla informacje o każdym module
     * @param kodeki Kolekcja kodeków i ich informacji, które mają być wyświetlone
     * @param rodzics Wskaźnik na główne okno programu
     */
    public OknoSzczegolow(Window rodzic, Collection<Kodek> kodeki)
    {

        super(rodzic, null, DEFAULT_MODALITY_TYPE);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Toolkit tk = Toolkit.getDefaultToolkit();

        Dimension rozmiar = tk.getScreenSize();

        //Na maksymalnie takiej powierzchni się wyświetli!
        double xx = rozmiar.width * 0.6;
        double yy = rozmiar.height * 0.6;

        //Proporcje jakie mają zostać zachowane / maks. rozmiar = stosunek wielk.
        int xpro = 900;
        int ypro = 500;

        double stosuneksz = xpro / (double) xx;
        double stosunekwy = ypro / (double) yy;

        if (stosuneksz > stosunekwy)
        {
            xpro = (int) (xpro / stosuneksz);
            ypro = (int) (ypro / stosuneksz);
            xx = (rozmiar.width - xx) / 2;
            yy = (rozmiar.height - ypro) / 2;
        } else
        {
            xpro = (int) (xpro / stosunekwy);
            ypro = (int) (ypro / stosunekwy);
            xx = (rozmiar.width - xpro) / 2;
            yy = (rozmiar.height - yy) / 2;
        }

        //Ustawienie ostatecznych rozmiarów okna
        setBounds((int) xx, (int) yy, xpro, ypro);

        tabPanel = new JTabbedPane();
        tabPanel.setOpaque(false);

        if (kodeki == null)
        {
            tabPanel.add(new PanelInfo("Brak modułów", null, "Coś nie tak"), "Moduł 0");
        } else
        {
            for (Kodek k : kodeki)
            {
                tabPanel.add(new PanelInfo(k.nazwa(), null, k.opis()), "Moduł " + k.id());

            }
        }
        getContentPane().add(tabPanel);
        setVisible(true);
    }
}
