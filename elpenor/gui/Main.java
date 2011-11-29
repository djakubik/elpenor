/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.gui;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Klasa startowa graficznego interfejsu użytkownika
 * @author Darian Jakubik
 */
public class Main
{

    /**
     * Uruchamia główny wątek aplikacji
     * @param args Argumenty wiersza poleceń
     */
    public static void main(String[] args)
    {

        MenedzerOkien menedzero = new MenedzerOkien();
        menedzero.start();

    }
}
