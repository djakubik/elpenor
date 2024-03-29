/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OknoParametrow.java
 *
 * Created on 2011-11-18, 17:04:46
 */
package elpenor.gui;

import elpenor.szkielet.Kodek;
import elpenor.szkielet.Kodek.Parametr;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Map;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/**
 *
 * @author darian
 */
public class OknoParametrow extends javax.swing.JDialog {

    JPanel panelParametru[] = new JPanel[5];
    JLabel nazwaParametru[] = new JLabel[5];
    JSpinner wartośćParametru[] = new JSpinner[5];
    Map<String, Integer> mapa;
    int początkoweWartości[] = new int[5];
    int wMin[] = new int[5];
    int wMax[] = new int[5];

    /** Creates new form OknoParametrow */
    public OknoParametrow(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);

        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setAlwaysOnTop(true);

        this.setLocation(parent.getX() + (parent.getWidth() - this.getWidth()) / 2,
                parent.getY() + (parent.getHeight() - this.getHeight()) / 2);

        this.przOk.setToolTipText("Zapisuje zmiany i wychodzi");
        this.przAnuluj.setToolTipText("Nie zapisuje zmian i wychodzi");
        this.przReset.setToolTipText("Przywraca wartości przed edycją");

        panelParametru[1] = panelParametru1;
        panelParametru[1].setVisible(false);

        panelParametru[2] = panelParametru2;
        panelParametru[2].setVisible(false);

        panelParametru[3] = panelParametru3;
        panelParametru[3].setVisible(false);

        panelParametru[4] = panelParametru4;
        panelParametru[4].setVisible(false);


        nazwaParametru[1] = nazwaParametru1;
        nazwaParametru[2] = nazwaParametru2;
        nazwaParametru[3] = nazwaParametru3;
        nazwaParametru[4] = nazwaParametru4;

        wartośćParametru[1] = wartośćParametru1;
        wartośćParametru[2] = wartośćParametru2;
        wartośćParametru[3] = wartośćParametru3;
        wartośćParametru[4] = wartośćParametru4;

    }

    public void ustawienia(final Kodek kodek, final Map<String, Integer> mapa)
    {
        setVisible(false);

        this.mapa = mapa;

        int i = 0;

        this.nazwaKodeku.setText(kodek.nazwa());

        for (final Parametr p : kodek.parametry()) {
            i++;
            final int j = i;

            Integer wartosc = mapa.get(p.nazwa());

            if (wartosc == null) {
                wartosc = p.domyslna();

            }

            nazwaParametru[j].setText(p.nazwa());
            wartośćParametru[j].setValue(wartosc);
            wMin[j] = p.min();
            wMax[j] = p.max();

            początkoweWartości[j] = wartosc;

            JFormattedTextField jftf = (JFormattedTextField) wartośćParametru[j].getEditor().getComponent(0);


            jftf.addFocusListener(new FocusListener() {

                public void focusGained(FocusEvent fe)
                {
                    ustawOpis(p.opis(), p.min(), p.max());
                }

                public void focusLost(FocusEvent fe)
                {
                    Integer integer = (Integer) wartośćParametru[j].getValue();
                    integer = (integer >= wMin[j]) ? ((integer <= wMax[j]) ? integer : wMax[j]) : wMin[j];
                    wartośćParametru[j].setValue(integer);
                    mapa.put(p.nazwa(), integer);

                }
            });

            panelParametru[j].setVisible(true);

        }

        setVisible(true);
    }

    private void ustawOpis(String tekst, int min, int max)
    {
        this.infoParametru.setText("<HTML>" + tekst.replace("&", "&amp;").
                replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
                + "\nMin: " + min + " , Max: " + max);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nazwaKodeku = new javax.swing.JLabel();
        prawyPanel = new javax.swing.JPanel();
        infoParametru = new javax.swing.JLabel();
        przOk = new javax.swing.JButton();
        przAnuluj = new javax.swing.JButton();
        przReset = new javax.swing.JButton();
        lewyPanel = new javax.swing.JPanel();
        nagłówek = new javax.swing.JLabel();
        panelParametru1 = new javax.swing.JPanel();
        nazwaParametru1 = new javax.swing.JLabel();
        wartośćParametru1 = new javax.swing.JSpinner();
        panelParametru2 = new javax.swing.JPanel();
        nazwaParametru2 = new javax.swing.JLabel();
        wartośćParametru2 = new javax.swing.JSpinner();
        panelParametru3 = new javax.swing.JPanel();
        nazwaParametru3 = new javax.swing.JLabel();
        wartośćParametru3 = new javax.swing.JSpinner();
        panelParametru4 = new javax.swing.JPanel();
        nazwaParametru4 = new javax.swing.JLabel();
        wartośćParametru4 = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        nazwaKodeku.setFont(new java.awt.Font("Arial", 1, 18));
        nazwaKodeku.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nazwaKodeku.setText("nazwaKodeku");

        infoParametru.setText("Informacje o wybranym parametrze.");

        przOk.setText("Ok");
        przOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                przOkActionPerformed(evt);
            }
        });

        przAnuluj.setText("Anuluj");
        przAnuluj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                przAnulujActionPerformed(evt);
            }
        });

        przReset.setText("Reset");
        przReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                przResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout prawyPanelLayout = new javax.swing.GroupLayout(prawyPanel);
        prawyPanel.setLayout(prawyPanelLayout);
        prawyPanelLayout.setHorizontalGroup(
            prawyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, prawyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(prawyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(infoParametru, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, prawyPanelLayout.createSequentialGroup()
                        .addComponent(przOk, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(przAnuluj, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(przReset, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                .addContainerGap())
        );
        prawyPanelLayout.setVerticalGroup(
            prawyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, prawyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoParametru, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(prawyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(przOk)
                    .addComponent(przAnuluj)
                    .addComponent(przReset))
                .addContainerGap())
        );

        nagłówek.setText("Wartości parametrów:");

        nazwaParametru1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nazwaParametru1.setText("parametr");

        wartośćParametru1.setToolTipText("");
        wartośćParametru1.setFocusable(false);
        wartośćParametru1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                wartośćParametru1FocusGained(evt);
            }
        });

        javax.swing.GroupLayout panelParametru1Layout = new javax.swing.GroupLayout(panelParametru1);
        panelParametru1.setLayout(panelParametru1Layout);
        panelParametru1Layout.setHorizontalGroup(
            panelParametru1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelParametru1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nazwaParametru1, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wartośćParametru1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelParametru1Layout.setVerticalGroup(
            panelParametru1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelParametru1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(nazwaParametru1)
                .addComponent(wartośćParametru1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        nazwaParametru2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nazwaParametru2.setText("parametr");

        javax.swing.GroupLayout panelParametru2Layout = new javax.swing.GroupLayout(panelParametru2);
        panelParametru2.setLayout(panelParametru2Layout);
        panelParametru2Layout.setHorizontalGroup(
            panelParametru2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelParametru2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nazwaParametru2, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wartośćParametru2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelParametru2Layout.setVerticalGroup(
            panelParametru2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelParametru2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(nazwaParametru2)
                .addComponent(wartośćParametru2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        nazwaParametru3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nazwaParametru3.setText("parametr");

        javax.swing.GroupLayout panelParametru3Layout = new javax.swing.GroupLayout(panelParametru3);
        panelParametru3.setLayout(panelParametru3Layout);
        panelParametru3Layout.setHorizontalGroup(
            panelParametru3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelParametru3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nazwaParametru3, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wartośćParametru3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelParametru3Layout.setVerticalGroup(
            panelParametru3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelParametru3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(nazwaParametru3)
                .addComponent(wartośćParametru3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        nazwaParametru4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nazwaParametru4.setText("parametr");

        javax.swing.GroupLayout panelParametru4Layout = new javax.swing.GroupLayout(panelParametru4);
        panelParametru4.setLayout(panelParametru4Layout);
        panelParametru4Layout.setHorizontalGroup(
            panelParametru4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelParametru4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nazwaParametru4, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wartośćParametru4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelParametru4Layout.setVerticalGroup(
            panelParametru4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelParametru4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(nazwaParametru4)
                .addComponent(wartośćParametru4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout lewyPanelLayout = new javax.swing.GroupLayout(lewyPanel);
        lewyPanel.setLayout(lewyPanelLayout);
        lewyPanelLayout.setHorizontalGroup(
            lewyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lewyPanelLayout.createSequentialGroup()
                .addGroup(lewyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lewyPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(nagłówek))
                    .addGroup(lewyPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelParametru1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(lewyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelParametru2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(lewyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelParametru3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lewyPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelParametru4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        lewyPanelLayout.setVerticalGroup(
            lewyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lewyPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(nagłówek)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelParametru1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelParametru2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelParametru3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelParametru4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lewyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(prawyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(nazwaKodeku, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nazwaKodeku, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lewyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(prawyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void przOkActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_przOkActionPerformed
    {//GEN-HEADEREND:event_przOkActionPerformed
        for (int i = 1; i < 5; i++) {
            Integer integer = (Integer) wartośćParametru[i].getValue();
            integer = (integer >= wMin[i]) ? ((integer <= wMax[i]) ? integer : wMax[i]) : wMin[i];

            mapa.put(nazwaParametru[i].getText(), integer);
        }
        this.dispose();
}//GEN-LAST:event_przOkActionPerformed

    private void przAnulujActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_przAnulujActionPerformed
    {//GEN-HEADEREND:event_przAnulujActionPerformed
        for (int i = 1; i < 5; i++) {
            mapa.put(nazwaParametru[i].getText(), początkoweWartości[i]);
        }
        this.dispose();
}//GEN-LAST:event_przAnulujActionPerformed

    private void przResetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_przResetActionPerformed
    {//GEN-HEADEREND:event_przResetActionPerformed
        for (int i = 1; i < 5; i++) {
            wartośćParametru[i].setValue(początkoweWartości[i]);
            mapa.put(nazwaParametru[i].getText(), początkoweWartości[i]);
        }
}//GEN-LAST:event_przResetActionPerformed

    private void wartośćParametru1FocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_wartośćParametru1FocusGained
    {//GEN-HEADEREND:event_wartośćParametru1FocusGained
    }//GEN-LAST:event_wartośćParametru1FocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run()
            {
                OknoParametrow dialog = new OknoParametrow(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel infoParametru;
    private javax.swing.JPanel lewyPanel;
    private javax.swing.JLabel nagłówek;
    private javax.swing.JLabel nazwaKodeku;
    private javax.swing.JLabel nazwaParametru1;
    private javax.swing.JLabel nazwaParametru2;
    private javax.swing.JLabel nazwaParametru3;
    private javax.swing.JLabel nazwaParametru4;
    private javax.swing.JPanel panelParametru1;
    private javax.swing.JPanel panelParametru2;
    private javax.swing.JPanel panelParametru3;
    private javax.swing.JPanel panelParametru4;
    private javax.swing.JPanel prawyPanel;
    private javax.swing.JButton przAnuluj;
    private javax.swing.JButton przOk;
    private javax.swing.JButton przReset;
    private javax.swing.JSpinner wartośćParametru1;
    private javax.swing.JSpinner wartośćParametru2;
    private javax.swing.JSpinner wartośćParametru3;
    private javax.swing.JSpinner wartośćParametru4;
    // End of variables declaration//GEN-END:variables
}
