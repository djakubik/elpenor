/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.gui;

import java.nio.ByteBuffer;
import java.util.BitSet;

/**
 *
 * @author Darian Jakubik
 */
public class BitMaster {

    ByteBuffer bufor;
    private int bufpoz = 0;
    byte[] wej;
    BitSet bity = new BitSet(64);
    private int bitpoz = 0;
    private int bitpozEnd = 0;
    int lz77_słownik;
    int lz77_wzorzec;

    
    public BitMaster(int lz77_słownik, int lz77_wzorzec, int buforSize) {

        this.lz77_słownik = (int) log2(lz77_słownik);
        this.lz77_wzorzec = (int) log2(lz77_wzorzec);

        bufor = ByteBuffer.allocateDirect(buforSize);
    }

    public void ustawBufor(byte[] bajty)
    {
        this.bufor.rewind();
        this.bufor.put(bajty);
    }
            
    public void ustawBit() {
        bity.set(bitpoz++);
    }

    public void wyczyscBit() {
        bity.clear(bitpoz++);
    }

    public void zapiszDuet(Duet duet) {
        int a = lz77_słownik + bitpoz;

        int offset = duet.offset;
        int długość = duet.długość;
        długość--;

        for (bitpoz = bitpoz; bitpoz < a; bitpoz++) {
            if (offset % 2 != 0) {
                bity.set(bitpoz);
            }
            offset >>= 1;
        }

        int b = lz77_wzorzec + bitpoz;

        for (bitpoz = bitpoz; bitpoz < b; bitpoz++) {
            if (długość % 2 != 0) {
                bity.set(bitpoz);
            }
            długość >>= 1;
        }

        if (bitpoz > 256) {
            przenieśDaneDoBufora();
        }
    }

    public void zapiszBajt(int i) {

        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        bitpoz++;

        if (bitpoz > 256) {
            przenieśDaneDoBufora();
        }
    }
    

    public void zapiszInt(int i) {

        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        bitpoz++;
        i >>>= 1;


        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        bitpoz++;
        i >>>= 1;


        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        bitpoz++;
        i >>>= 1;


        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        i >>>= 1;
        bitpoz++;
        if (i % 2 != 0) {
            bity.set(bitpoz);
        }
        bitpoz++;


        if (bitpoz > 256) {
            przenieśDaneDoBufora();
        }
    }

    public boolean odczytajBit() {
        return bity.get(bitpoz++);
    }

    public Duet odczytajDuet() {

        if (bitpoz > 256) {
            przenieśDaneZBufora();
        }

        int a = lz77_słownik + bitpoz - 1;
        int aa = 0;

        for (int i = a; bitpoz <= i; i--) {
            aa <<= 1;
            if (bity.get(i)) {
                aa++;
            }
        }

        bitpoz = ++a;

        int b = lz77_wzorzec + bitpoz - 1;
        int bb = 0;

        for (int i = b; bitpoz <= i; i--) {
            bb <<= 1;
            if (bity.get(i)) {
                bb++;
            }
        }

        bitpoz = ++b;

        return new Duet(aa, ++bb);

    }

    public int odczytajBajt() {

        if (bitpoz > 256) {
            przenieśDaneZBufora();
        }


        bitpoz += 8;
        int i = 0, j = bitpoz - 1;

        if (bity.get(j--)) {
            i++;
        }
        i <<= 1;
        if (bity.get(j--)) {
            i++;
        }
        i <<= 1;
        if (bity.get(j--)) {
            i++;
        }
        i <<= 1;
        if (bity.get(j--)) {
            i++;
        }
        i <<= 1;
        if (bity.get(j--)) {
            i++;
        }
        i <<= 1;
        if (bity.get(j--)) {
            i++;
        }
        i <<= 1;
        if (bity.get(j--)) {
            i++;
        }
        i <<= 1;
        if (bity.get(j--)) {
            i++;
        }

        return i;
    }

    public byte[] zakończ() {

        przenieśKońcoweDaneDoBufora();
        //bufpoz;
//        if (bufpoz != bufor.array().length) {
//            System.out.println("ERROR");
//        }
        bufor.rewind();
        byte[] ret = new byte[bufpoz];
        bufor.get(ret);
        return ret;
    }

    private void przenieśDaneDoBufora() {

        byte[] doBufora = bity.toByteArray();

        bufor.position(bufpoz);

        if (doBufora.length < 32) {
            bufor.put(doBufora, 0, doBufora.length);
            bufor.put(new byte[32 - doBufora.length]);
        } else {
            bufor.put(doBufora, 0, 32);
        }
        bufpoz += 32;

        BitSet b = new BitSet(512);

        for (int i = 256; i < bitpoz; i++) {
            if (bity.get(i)) {
                b.set(i - 256);
            }
        }

        bity.clear();
        bity = b;
        bitpoz -= 256;



    }

    public void przenieśKońcoweDaneDoBufora() {

        byte[] doBufora = bity.toByteArray();
        int ilość = (int) Math.ceil((double) bitpoz / 8);



        bufor.position(bufpoz);
        if (doBufora.length < 32) {
            bufor.put(doBufora, 0, doBufora.length);
        } else {
            bufor.put(doBufora, 0, doBufora.length);
            System.out.println("BUF MAY BE OVERFLOWED");
        }
        bufpoz += doBufora.length;

        System.out.println("LBUF USED: " + bufor.position());
        System.out.println("LBUF LEFT: " + bufor.remaining());

    }

    private void przenieśDaneZBufora() {

        bufor.position(bufpoz);
        if (bufor.hasRemaining()) {
            int ile = (384 - bitpozEnd) / 8 + 32;
            byte[] zBufora = new byte[48];

            if (bufor.remaining() < ile) {
                ile = bufor.remaining();
            }

            bufor.get(zBufora, 0, ile);



            bufpoz += ile;

            BitSet b = new BitSet(512);

            int j = 0;
            for (int i = bitpoz; i < bitpozEnd; i++, j++) {
                if (bity.get(i)) {
                    b.set(j);
                }
            }

            bitpozEnd = j;

            bitpoz = 0;

            bity.clear();
            bity = b;

            for (j = 0; j < ile; j++) {
                int i = zBufora[j];

                if (i % 2 != 0) {
                    bity.set(bitpozEnd);
                }
                i >>= 1;
                bitpozEnd++;
                if (i % 2 != 0) {
                    bity.set(bitpozEnd);
                }
                i >>= 1;
                bitpozEnd++;
                if (i % 2 != 0) {
                    bity.set(bitpozEnd);
                }
                i >>= 1;
                bitpozEnd++;
                if (i % 2 != 0) {
                    bity.set(bitpozEnd);
                }
                i >>= 1;
                bitpozEnd++;
                if (i % 2 != 0) {
                    bity.set(bitpozEnd);
                }
                i >>= 1;
                bitpozEnd++;
                if (i % 2 != 0) {
                    bity.set(bitpozEnd);
                }
                i >>= 1;
                bitpozEnd++;
                if (i % 2 != 0) {
                    bity.set(bitpozEnd);
                }
                i >>= 1;
                bitpozEnd++;
                if (i % 2 != 0) {
                    bity.set(bitpozEnd);
                }
                bitpozEnd++;
            }


        }

    }

    public void przenieśPierwszeDaneZBufora() {

        bufor.rewind();
        if (bufor.hasRemaining()) {
            int ile = 48;
            byte[] zBufora = new byte[64];

            bufor.position(0);
            if (bufor.remaining() < 64) {
                ile = bufor.remaining();
            }

            bufor.get(zBufora, 0, ile);

            bitpoz = 0;
            bitpozEnd = ile * 8;
            bufpoz = ile;

            bity.clear();

            for (int j = 0; j < ile; j++) {
                int i = zBufora[j];


                if (i % 2 != 0) {
                    bity.set(bitpoz);
                }
                i >>= 1;
                bitpoz++;
                if (i % 2 != 0) {
                    bity.set(bitpoz);
                }
                i >>= 1;
                bitpoz++;
                if (i % 2 != 0) {
                    bity.set(bitpoz);
                }
                i >>= 1;
                bitpoz++;
                if (i % 2 != 0) {
                    bity.set(bitpoz);
                }
                i >>= 1;
                bitpoz++;
                if (i % 2 != 0) {
                    bity.set(bitpoz);
                }
                i >>= 1;
                bitpoz++;
                if (i % 2 != 0) {
                    bity.set(bitpoz);
                }
                i >>= 1;
                bitpoz++;
                if (i % 2 != 0) {
                    bity.set(bitpoz);
                }
                i >>= 1;
                bitpoz++;
                if (i % 2 != 0) {
                    bity.set(bitpoz);
                }
                bitpoz++;
            }

            bitpoz = 0;
        }

    }

    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    private void wypiszBufor(ByteBuffer b) {
        System.out.print("WART: ");
        b.rewind();
        while (b.hasRemaining()) {
            byte bb = b.get();
//            if (Character.isLetterOrDigit(bb)) {
//                System.out.print(Character.toChars(bb));
//                System.out.print("-");
//            } else {
            System.out.print(bb + "-");
//            }
        }
        b.rewind();
        System.out.println("|");
    }

    public void wypiszBajty(byte[] b) {

        System.out.print(b.length + ": ");
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i] + ".");
        }
        System.out.println('|');
    }

    public void wypiszZawartość() {
        System.out.print(bitpoz + ": ");
        for (int i = 0; i < bitpoz; i++) {

            if (bity.get(i)) {
                System.out.print("1-");
            } else {
                System.out.print("0-");
            }
        }
        System.out.println('|');
    }

    public void wypiszBajtowo() {
        byte[] b = bity.toByteArray();

        System.out.print(b.length + ": ");
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i] + ".");
        }
        System.out.println('|');
    }
}
