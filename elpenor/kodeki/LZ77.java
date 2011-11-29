/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.kodeki;

import elpenor.gui.BitMaster;
import elpenor.gui.Duet;
import elpenor.szkielet.Dane;
import elpenor.szkielet.Kodek;
import elpenor.szkielet.Metadane;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.zip.DataFormatException;

/**
 *
 * @author Darian Jakubik
 */
public class LZ77 extends Kodek {

    private static final int ID = 5;
    
    private int LZ77_SŁOWNIK = 2048;
    private int LZ77_WZORZEC = 16;
    private int LZ77_KRYTERIUM = 1;
    
    private String[] raporty = new String[1000];
    private int tString = 0;
    private double[] pojemności = new double[1000];
    private int tInt = 0;
    
    @Override
    public String nazwa() {
        return "LZ77";
    }

    @Override
    public String opis() {
        return "Opis będzie.. wkrótce";
    }

    @Override
    public int id() {
        return ID;
    }
    
    @Override
    public Dane zakoduj(Dane b, Map<String, Integer> par) {

        byte[] wej = b.bufor();
        Metadane metadane = b.meta();
        Integer dp;
        
        
        dp = par.get("Słownik");
        LZ77_SŁOWNIK = (dp == null) ? 12 : dp;
        metadane.dodajWartosc(LZ77_SŁOWNIK);
        LZ77_SŁOWNIK  = 1 << LZ77_SŁOWNIK;
        
        dp = par.get("Wzorzec");
        LZ77_WZORZEC = (dp == null) ? 5 : dp;
        metadane.dodajWartosc(LZ77_WZORZEC);
        LZ77_WZORZEC = 1 << LZ77_WZORZEC;
        
        dp = par.get("Kryterium");
        LZ77_KRYTERIUM = (dp == null) ? 1 : dp;
        metadane.dodajWartosc(LZ77_KRYTERIUM);
        
        
        int wejPoz = 0;
        int iloscDanych = wej.length;

        System.out.println("Koduje " + iloscDanych + " B");

        int iloscA = 0;
        int iloscB = 0;

        long czas = 0;
        long czasA = 0;
        long tCzasA;

        BitMaster bitMaster = new BitMaster(LZ77_SŁOWNIK, LZ77_WZORZEC, (int)(1.8*iloscDanych));
        Duet duet;

        ByteBuffer słownik = ByteBuffer.allocateDirect(LZ77_SŁOWNIK);

        ByteBuffer wzorzec = ByteBuffer.allocateDirect(LZ77_WZORZEC);

        wejPoz = pobierzDaneDoWzorca(wzorzec, wej, wejPoz, LZ77_WZORZEC);

        
        int iloscWszystkich = iloscDanych;
        
        
        bitMaster.zapiszInt(iloscDanych);

        while (iloscDanych > 0) {
            
            tCzasA = System.nanoTime();

            duet = wyszukajWzorzec(słownik, wzorzec);

            if (duet.długość > LZ77_KRYTERIUM) {

                bitMaster.ustawBit();
                bitMaster.zapiszDuet(duet);
                
                przepiszDoSłownika(słownik, wzorzec, duet.długość);

                wejPoz = pobierzDaneDoWzorca(wzorzec, wej, wejPoz, duet.długość);

                duet.długość++;
                bitMaster.zapiszBajt(wzorzec.get(0));
                przepiszDoSłownika(słownik, wzorzec, 1);


                iloscA++;

            } else {

                duet.długość = 1;
                bitMaster.wyczyscBit();
                bitMaster.zapiszBajt(wzorzec.get(0));

                przepiszDoSłownika(słownik, wzorzec, 1);

                iloscB++;
            }

            iloscDanych -= duet.długość;
            wejPoz = pobierzDaneDoWzorca(wzorzec, wej, wejPoz, 1);

            czasA += (System.nanoTime() - tCzasA)/1000;
            
            if (czasA>czas)
            {
                czas += 10000000;
                System.out.println("Czas pracy: "+ czasA/1000000 + " s , Pozostało: "+ (double)iloscDanych/iloscWszystkich*100+ " % danych");
            }

        }

        assert iloscDanych == 0;
        iloscDanych = wej.length;
        
        wej = bitMaster.zakończ();

        Dane dane = new Dane(wej, 0, wej.length);
        dane.dodajMeta(metadane);
        
        System.out.println("Parametry: S=" + LZ77_SŁOWNIK + " ,W=" + LZ77_WZORZEC + " ,K=" + LZ77_KRYTERIUM);
        System.out.println("Raport: A=" + iloscA + " ,B=" + iloscB);
        System.out.println("Raport: bA=" + (9 + log2(LZ77_SŁOWNIK) + log2(LZ77_WZORZEC)) + " ,bB=9");
        System.out.println("Raport: mA=" + iloscA * (9 + log2(LZ77_SŁOWNIK) + log2(LZ77_WZORZEC)) / 8 + " ,mB=" + (double) iloscB * 9 / 8);
        System.out.println("Raport: AB=" + Math.ceil((iloscA * (9 + log2(LZ77_SŁOWNIK) + log2(LZ77_WZORZEC)) + (double) iloscB * 9) / 8) + "  (" + (iloscA * (9 + log2(LZ77_SŁOWNIK) + log2(LZ77_WZORZEC)) + (double) iloscB * 9) / 8 + ")");
        System.out.println("Raport: P=" + iloscDanych);
        System.out.println("Raport: SK=" + Math.ceil((iloscA * (9 + log2(LZ77_SŁOWNIK) + log2(LZ77_WZORZEC)) + iloscB * 9) / 8) / iloscDanych * 100 + " %");
        //System.out.println("Raport: TA=" + czasA + " ,TB=" + czasB + " ,TC=" + czasC + " ,TD=" + czasD + " ,T=" + czas);
        System.out.println("Raport: T=" + czasA);
        
        return dane;

    }

    @Override
    public Dane odkoduj(Dane b, PrintWriter pw) {

        LZ77_SŁOWNIK = 1 << b.meta().pobierz_wartosc();
        LZ77_WZORZEC = 1 << b.meta().pobierz_wartosc();
        LZ77_KRYTERIUM = b.meta().pobierz_wartosc();
        
        long tCzasA = System.nanoTime();

        BitMaster bitMaster = new BitMaster(LZ77_SŁOWNIK, LZ77_WZORZEC, b.bufor().length);

        ByteBuffer słownik = ByteBuffer.allocateDirect(LZ77_SŁOWNIK);


        bitMaster.ustawBufor(b.bufor());
        bitMaster.przenieśPierwszeDaneZBufora();

        int iloscDanych = bitMaster.odczytajBajt();
        iloscDanych += bitMaster.odczytajBajt() * 256;
        iloscDanych += bitMaster.odczytajBajt() * 256 * 256;
        iloscDanych += bitMaster.odczytajBajt() * 256 * 256 * 256;

        ByteBuffer wyj = ByteBuffer.allocateDirect(iloscDanych);

        System.out.println("Odkodowuje " + b.bufor().length + " B do " + iloscDanych + " B");
        System.out.println("Parametry: S=" + LZ77_SŁOWNIK + " ,W=" + LZ77_WZORZEC + " ,K=" + LZ77_KRYTERIUM);

        int procent = 10;
        for (int i = 0; i < iloscDanych; ++i) {
            if (bitMaster.odczytajBit()) {

                Duet tDuet = bitMaster.odczytajDuet();
                byte tInt = (byte) bitMaster.odczytajBajt();

                i += tDuet.długość;

                if (i < iloscDanych) {
                    
//                    System.out.print("(" + i + ")" + tDuet.offset + " - ");
//                    System.out.print(tDuet.długość + "| ");
//                    System.out.println(tInt);

                    byte[] zeSłownika = pobierzZeSłownika(słownik, tDuet);

//                    System.out.print("D1: ");
//                    for(int t = 0; t <zeSłownika.length; t++)
//                    {
//                        System.out.print(zeSłownika[t]+".");
//                    }
//                    System.out.println(" | "+tInt);

                    wyj.put(zeSłownika);
                    wyj.put(tInt);

                    wrzućDoSłownika(słownik, tInt);
                } else {
                    tDuet.długość -= i - iloscDanych;
                    byte[] zeSłownika = pobierzZeSłownika(słownik, tDuet);

//                    System.out.print("D2: ");
//                    for(int t = 0; t <zeSłownika.length; t++)
//                    {
//                        System.out.print(zeSłownika[t]+".");
//                    }

                    wyj.put(zeSłownika);
                }


            } else {
                byte tInt = (byte) bitMaster.odczytajBajt();

                wyj.put(tInt);
                
//                System.out.println("W: "+tInt);

                wrzućDoSłownika(słownik, tInt);
//                System.out.print("(" + i + ")");
//                System.out.println(tInt);

            }
            //bitMaster.wypiszZawartość();
            //bitMaster.wypiszBajtowo();
            if (i>iloscDanych*procent/100) 
            {
                System.out.println("Data decoded: "+procent+"%");
                procent += 10;
            }
        }

        tCzasA = System.nanoTime() - tCzasA;
        String raport = "Czas Odkodowywania: " + tCzasA / 1000;
        System.out.println(raport);
        raporty[tString++] = raport;

        wyj.rewind();
        byte[] ret = new byte[iloscDanych];
        wyj.get(ret);

        Dane dane = new Dane(ret, 0, iloscDanych);
        dane.dodajMeta(b.meta());
        
        return dane;
    }

    @Override
    public Collection<Parametr> parametry() {
        
        Collection<Parametr> kolekcjaParametrów = new ArrayList<Parametr>();
        kolekcjaParametrów.add(new Kodek.Parametr("Słownik", "Określa w bitach długość słownika", 8, 16, 12));
        kolekcjaParametrów.add(new Kodek.Parametr("Wzorzec", "Określa w bitach długość wzorca", 4, 8, 5));
        kolekcjaParametrów.add(new Kodek.Parametr("Kryterium", "Określa minimalną długość podciągu słownika, który będzie zapisywany w formacie trójki danych", 0, 3, 1));
      
        return kolekcjaParametrów;
    }

    private Duet wyszukajWzorzec(ByteBuffer słownik, ByteBuffer wzorzec) {

        słownik.rewind();
        wzorzec.rewind();

        int dsłownik = słownik.remaining();
        int dwzorzec = wzorzec.remaining();
        int i, j, k, maxoff = 0, maxdł = 0;

        Duet duet;


        for (i = 0; i < dsłownik; i++) {


            for (j = 0, k = i; j < dwzorzec && k < dsłownik; j++, k++) {
                byte a = słownik.get(k);
                byte b = wzorzec.get(j);

                if (a != b) {
                    break;
                }
            }


            if (j > maxdł) {
                maxoff = i;
                maxdł = j;
            }

        }

        duet = new Duet(maxoff, maxdł);

        return duet;
    }

    private void przepiszDoSłownika(ByteBuffer słownik, ByteBuffer wzorzec, int ile) {

        byte[] doWzorca = new byte[LZ77_WZORZEC - ile];
        byte[] doSłownika = new byte[ile];

        wzorzec.rewind();
        wzorzec.get(doSłownika, 0, ile);
        wzorzec.get(doWzorca, 0, LZ77_WZORZEC - ile);

        wzorzec.clear();
        wzorzec.put(doWzorca);

        byte[] starySłownik = new byte[LZ77_SŁOWNIK - ile];
        słownik.position(ile);
        słownik.get(starySłownik, 0, LZ77_SŁOWNIK - ile);

        słownik.clear();
        słownik.put(starySłownik);
        słownik.put(doSłownika);

        słownik.rewind();
    }

    private byte[] pobierzZeSłownika(ByteBuffer słownik, Duet duet) {

        int ile = duet.długość;
        int pozycja = duet.offset;

        byte[] zeSłownika = new byte[ile];

        słownik.position(pozycja);
        słownik.get(zeSłownika, 0, ile);

        byte[] starySłownik = new byte[LZ77_SŁOWNIK - ile];
        słownik.position(ile);
        słownik.get(starySłownik, 0, LZ77_SŁOWNIK - ile);

        słownik.clear();
        słownik.put(starySłownik);
        słownik.put(zeSłownika);
        słownik.rewind();

        return zeSłownika;
    }

    private void wrzućDoSłownika(ByteBuffer słownik, byte bajt) {

        byte[] starySłownik = new byte[LZ77_SŁOWNIK - 1];
        słownik.position(1);
        słownik.get(starySłownik, 0, LZ77_SŁOWNIK - 1);

        słownik.clear();
        słownik.put(starySłownik);
        słownik.put(bajt);
        słownik.rewind();

    }

    private int pobierzDaneDoWzorca(ByteBuffer wzorzec, byte[] wej, int wejpoz, int długość) {

        wzorzec.position(LZ77_WZORZEC - długość);

        if ((wej.length - wejpoz) < długość) {
            długość = wej.length - wejpoz;
        }

        wzorzec.put(wej, wejpoz, długość);

        return wejpoz += długość;

    }

    private void wypiszBufor(ByteBuffer b) {
        System.out.print("WART: ");
        b.rewind();
        while (b.hasRemaining()) {
            byte bb = b.get();
            if (Character.isLetterOrDigit(bb)) {
                System.out.print(Character.toChars(bb));
                System.out.print("-");
            } else {
                System.out.print(bb + "-");
            }
        }
        b.rewind();
        System.out.println("|");
    }

    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }
    
}
