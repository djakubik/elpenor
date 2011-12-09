/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elpenor.gui;

import elpenor.szkielet.Dane;
import java.awt.*;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.Map;
import javax.swing.*;

/**
 * Wątek, dzięki któremu jest tworzone GUI aplikacji
 * @author Darian Jakubik
 */
public class MenedzerOkien extends Thread {

    private JFrame okno;
    private JTabbedPane tabPanel;
    
    /**
     * Uruchamia metodę otwarcia głównego okna
     */
    MenedzerOkien() {


        oknoGlowne();
    }

    /**
     * Tworzy okno główne o zaimplementowanych rozmiarach
     */
    public void oknoGlowne() {

        //Logger.getLogger(PanelGlowny.class.getName()).log(Level.OFF,  ex);
        if (okno != null) {

            okno.setVisible(false);
            okno.dispose();
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;


        okno = new JFrame("Elpenor");
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit tk = Toolkit.getDefaultToolkit();

        Dimension rozmiar = tk.getScreenSize();

        //Na maksymalnie takiej powierzchni się wyświetli!
        double xx = rozmiar.width * 0.8;
        double yy = rozmiar.height * 0.8;

        //Proporcje jakie mają zostać zachowane / maks. rozmiar = stosunek wielk.
        int xpro = 900;
        int ypro = 610;

        double stosuneksz = xpro / (double) xx;
        double stosunekwy = ypro / (double) yy;

        if (stosuneksz > stosunekwy) {
            xpro = (int) (xpro / stosuneksz);
            ypro = (int) (ypro / stosuneksz);
            xx = (rozmiar.width - xx) / 2;
            yy = (rozmiar.height - ypro) / 2;
        } else {
            xpro = (int) (xpro / stosunekwy);
            ypro = (int) (ypro / stosunekwy);
            xx = (rozmiar.width - xpro) / 2;
            yy = (rozmiar.height - yy) / 2;
        }

        //Ustawienie ostatecznych rozmiarów okna
        okno.setBounds((int) xx, (int) yy, xpro, ypro);

        tabPanel = new JTabbedPane();
        tabPanel.setOpaque(false);


        tabPanel.add(new PanelKompresji(okno), "Kompresja"); // Dodaj panel kompresji
        tabPanel.add(new PanelDekompresji(okno), "Dekompresja"); // Dodaj panel dekompresji
        // tabPanel.add(new PanelWKonwersji(), "Konwersja"); //Dodaj panel konwersji


        okno.getContentPane().add(tabPanel);
        okno.setVisible(true);



    }

    /**
     * 
     * @return zwraca przykładowy tekst
     */
    private byte[] dajDane() {
        String s = "[Mr.Hudson]"
                + "Zatańczmy stylowo, zatańczmy przez chwilę"
                + "Niebo może zaczekać, tylko spoglądamy w niebiosa"
                + "Z nadzieją na najlepsze, ale oczekując najgorszego"
                + "Przyniesiesz złą wiadomość czy nie?"
                + "Pozwól nam umrzeć młodo lub żyć wiecznie"
                + "Nie mamy nadludzkiej mocy, lecz nigdy nie mówimy nigdy"
                + "Siedząc w piaskownicy, życie jest krótką wycieczką"
                + "Muzyka jest stworzona dla smutnego człowieka"
                + ""
                + "[Refren: Mr. Hudson]"
                + "Wiecznie młody, chce być wiecznie młody"
                + "Czy naprawdę chcesz żyć wiecznie, na wieki wieków?"
                + "Wiecznie młody, chce być wiecznie młody"
                + "Czy naprawdę chcesz żyć wiecznie, na wieki wieków?"
                + ""
                + "[Jay-Z w trakcie refrenu]"
                + "Uhh, młody"
                + "Ay(co) może być dla ciebie najlepsze dziś"
                + "Będzie najgorsze jutro"
                + "Ale my nie myślimy jeszcze tak daleko"
                + "Wiesz człowiek"
                + ""
                + "[Jay-Z]"
                + "Więc mieszkamy jak w filmie"
                + "Gdzie słońce jest zawsze wysoko a ty nigdy sie nie zestarzejesz"
                + "I gdzie szampan zawsze jest zimny, a muzyka zawsze dobra"
                + "Ładne dziewczyny przypadkiem wstępują do sąsiedztwa"
                + "I siadają swoim gorącym tyłkiem na masce pięknego samochodu"
                + "Bez zmarszczek dziś, bo to nie jutro"
                + "Po prostu doskonały obraz dni, które trwają całe życie"
                + "I nigdy się nie kończy bo wszystko co trzeba zrobić to wcisnąć przewiń"
                + "Więc zatrzymajmy się na moment, zajarajmy zioło, napijmy się wina"
                + "Wspomnienia, gadka o niczym, zawsze młody jest w twoim umyśle"
                + "Zostaw ślad którego nie wymażą ani w przestrzeni ani czasie"
                + "Więc kiedy reżyser krzyczy ciecie"
                + "Będę w porządku"
                + "Jestem, wiecznie młody"
                + ""
                + "[Refren: Mr. Hudson]"
                + ""
                + "[Jay-Z]"
                + "Nie bój się kiedy ani dlaczego,nie bój się zbytnio kiedy żyjemy"
                + "Życie jest do przeżycia, nie żyj spięty, dopóki nie pójdziesz gdzieś wysoko do nieba"
                + "Nie bój się śmierci, ja przeżyje miliony lat, żegnaj"
                + "Więc teraz nie dla legendy jestem wiecznie młody, moje imię może przetrwać"
                + "Przez najciemniejsze bloki, nad kuchenkami, nad naczynkami do smażenia cracku"
                + "Moje imię będzie przekazywane z pokolenia na pokolenie podczas debat u fryzjera"
                + "Młody slams zawisł tutaj, pokaż to czarnuchom stąd"
                + "Odrobina ambicji, po prostu czym stać się tutaj (tak)"
                + "I jak ojciec streszcza synowi opowieść do jego uszu"
                + "Ciągle młodszy, młodszy każdego roku (tak)"
                + "Więc jeśli mnie kochasz złotko w ten sposób możesz dać mi znać"
                + "Nigdy nie pozwól mi odejść, w ten sposób możesz dać mi znać, złotko"
                + ""
                + "[Refren: Mr. Hudson]"
                + ""
                + "[Jay-Z]"
                + "Trzaśnij drzwiami Bentleya"
                + "Wyskocz z porsche"
                + "Pojaw się na liście Forbesa"
                + "Wspaniale (wytrzymaj)"
                + "Czarnuchy myślały że straciłem to"
                + "Będą gadali bzdury"
                + "Będę mówił więcej bzdetów"
                + "Dostaną mdłości (wytrzymaj)"
                + "Będę tu zawsze wiesz"
                + "Jestem na spadającym gównie (?)"
                + "I nie jestem wykluczony"
                + "Nigdy nie zrezygnuję"
                + "Mniej niż 4 kraty"
                + "Guru wprowadzi refren"
                + "Zrozumiałeś już?"
                + "Maluję Ci portret młodości";

        String s2 = "The wife of a rich man fell sick, and as she felt that her end was drawing near, she called her only daughter to her bedside and said, \"Dear child, be good and pious, and then the good God will always protect thee, and I will look down on thee from heaven and be near thee.\" Thereupon she closed her eyes and departed. Every day the maiden went out to her mother's grave and wept, and she remained pious and good. When winter came the snow spread a white sheet over the grave, and when the spring sun had drawn it off again, the man had taken another wife. ";
        s2 += "The woman had brought two daughters into the house with her, who were beautiful and fair of face, but vile and black of heart. Now began a bad time for the poor step-child. \"Is the stupid goose to sit in the parlour with us?\" said they. \"He who wants to eat bread must earn it; out with the kitchen-wench.\" They took her pretty clothes away from her, put an old grey bedgown on her, and gave her wooden shoes. \"Just look at the proud princess, how decked out she is!\" they cried, and laughed, and led her into the kitchen. There she had to do hard work from morning till night, get up before daybreak, carry water, light fires, cook and wash. Besides this, the sisters did her every imaginable injury they mocked her and emptied her peas and lentils into the ashes, so that she was forced to sit and pick them out again. In the evening when she had worked till she was weary she had no bed to go to, but had to sleep by the fireside in the ashes. And as on that account she always looked dusty and dirty, they called her Cinderella.";
        
        String s3 = "1 października 1962 roku cztery radzieckie okręty typu Foxtrot B-4, B-36, B-59 oraz B-130 wypłynęły z bazy na półwyspie Kolskim udając się w rejon Karaibów w misji wsparcia dostawy broni na Kubę w ramach radzieckiej operacji \"Anadyr\". Oprócz konwencjonalnych torped, każdy z tych okrętów miał na swoim wyposażeniu jedną torpedę z głowicą jądrową. Dodatkowe dwa okręty – jeden projektu 641 oraz jeden projektu 611/Zulu znajdowały się już w tym czasie na zachodnim Atlantyku[1]. W trakcie wywołanego radziecką operacją \"kryzysu kubańskiego\", wszystkie radzieckie okręty zostały wykryte przez amerykańskie siły podwodne – jeden projektu 611 oraz cztery Foxtroty[1]. Piąta jednostka projektu 641 (o numerze 945) z powodu problemów technicznych zmuszona została do wynurzenia się, i została wykryta na powierzchni w drodze powrotnej do Związku Radzieckiego w towarzystwie holownika \"Palmir\". Także jednostka projektu 611 została wykryta na powierzchni, w trakcie tankowania paliwa z tankowca \"Terek\"[1]. Stany Zjednoczone i Kanada podjęły zakrojoną na szeroka skalę operację zmierzającą do odnalezienia czterech jednostek, które opuściły bazę 1 października, z użyciem startujących z Argentii w Nowej Fundlandii i Labradorze oraz baz w Kanadzie i Stanach Zjednoczonych samolotów patrolowych P2V Neptune oraz P3V Orion. W poszukiwania włączyły się także nie wyposażone do zadań przeciwpodwodnych samoloty rozpoznania powietrznego amerykańskich sił powietrznych (US Air Force) RB-47 oraz RB-50[1]. Dodatkowo, w skład sił tworzących amerykańską linię blokady przed Kubą, wchodził lotniskowiec ZOP USS \"Essex\" (CV-9), w późniejszym czasie wsparty przed dwa kolejne lotniskowce zwalczania okrętów podwodnych[1]. Sytuacja osiągnęła punkt krytyczny 24 października kilka minut po godzinie 10 rano czasu waszyngtońskiego, kiedy amerykański sekretarz obrony Robert McNamara poinformował prezydenta Kennedy'ego, że dwa radzieckie frachtowce znajdują się już jedynie kilka mil morskich od strefy blokady, gdzie powinny zostać przechwycone przez amerykańskie niszczyciele oraz, że każdemu ze statków towarzyszy jeden okręt podwodny. McNamara ostrzegł też prezydenta o niebezpieczeństwie tej sytuacji. Biały Dom podjął decyzję, że amerykańskie okręty powinny za pomocą sonaru wydać radzieckim okrętom polecenie wynurzenia się i identyfikacji. W razie odmowy wykonania polecenia, amerykańskie okręty miały użyć małych ładunków wybuchowych w charakterze sygnalizatorów dźwiękowych[1].";
        s3 += "Trzy z czterech Foxtrotów zostały zmuszone do wynurzenia się w obecności sił amerykańskich na północny wschód od linii blokady[2]. W rzeczywistości ich dowódcy zignorowali sygnały wzywające do wynurzenia, jednak nacisk amerykańskich niszczycieli trwał tak długo, że radzieckie okręty musiały się wynurzyć celem wymiany powietrza w jednostkach, naładowania akumulatorów oraz usunięcia problemów mechanicznych. Pierwszy został wykryty B-59, który 25 października 1962 roku został namierzony na powierzchni przez startujący z lądu samolot patrolowy 350 mil na południowy zachód od Bermudów. Okręt zanurzył się, 27 października został jednak ponownie wykryty w zanurzeniu za pomocą boi sonarowych przez samoloty z grupy ZOP lotniskowca \"Randolph\" (CV-15), po czym był śledzony przez helikoptery HS-5 tego zespołu za pomocą boi oraz detektorów anomalii magnetycznych (Magnetic Anomaly Detector – MAD). Śledzący rozpoczęli wrzucanie do wody niewielkich ładunków wybuchowych, mających sygnalizować dźwiękiem nakaz wynurzenia się i identyfikacji[2]. Okręt jednak nie wynurzył się. Po przybyciu na miejsce trzech niszczycieli, amerykańskie okręty również uchwyciły kontakt i ponowiły sygnalizacje za pomocą ładunków wybuchowych. Dowódca radzieckiego okrętu otrzymał wcześniej od swojego dowództwa zestaw amerykańskich procedur wynurzania[2] i rozumiał więc amerykańskie sygnały, jednakże po wielokrotnych wybuchach ładunków zrzucanych wcześniej do wody przez śmigłowce, a następnie przez niszczyciele, spodziewał się podjęcia wkrótce przez nie rzeczywistych działań bojowych. Wydał wobec tego rozkaz przygotowania do użycia torpedy z głowicą nuklearną[3], został jednak powstrzymany[4]. Według ujawnionych później raportów radzieckiego wywiadu, dowódca jednostki powiedzieć miał wówczas[4]:";
        s3 += "Według relacji członków załogi radzieckiego okrętu, rozkaz przygotowania torpedy jądrowej był wynikiem utraty panowania nad sobą przez dowódcę jednostki, co jest współcześnie podawane w wątpliwość przez niektóre źródła, których autorzy wychodzą z założenia, że dowódcy okrętów podwodnych są starannie dobierani, w związku z czym sytuacja utraty kontroli nad sobą nie mogłaby się w praktyce zdarzyć. Sytuacja była tym bardziej dramatyczna, że zdarzenie to miało miejsce zaledwie kilka godzin po zestrzeleniu nad Kubą amerykańskiego samolotu U-2[5]. Po chwili, wieczorem 27 października B-59 wynurzył się. Na zadane lampą sygnałową przez amerykańskie okręty pytanie \"co za okręt?\", dowódca radzieckiej jednostki odpowiedział \"Korabl X\"[2] (okręt X). Do 29 października radziecki okręt w asyście amerykańskich niszczycieli ładował na powierzchni akumulatory, po czym zanurzył się i ruszył na wschód, aż do 6 listopada śledzony przez amerykańskie samoloty patrolowe z użyciem radarów i boi sonarowych[2]."
                + "B-36 został wykryty 28 października 1962 roku przez niszczyciel USS \"Charles P. Cecil\" (DD-835) typu Gearing, za pomocą radaru, który wykrył częściowo wystający nad powierzchnię wody kiosk okrętu podwodnego[2]. Urządzenie wykrywające emisję elektromagnetyczną radaru, w które wyposażony był radziecki okręt, nie wykryło radaru amerykańskiego okrętu, jednakże szybko płynący niszczyciel został wykryty przez sonar pasywny jednostki radzieckiej, która zanurzyła się, pozostając w zanurzeniu przez 35 godzin[2]. Wkrótce po zanurzeniu się B-36, \"Charles P. Cecil\" utracił z nim kontakt na skutek awarii sonaru. Po przywróceniu sprawności urządzenia, radziecka jednostka została ponownie odnaleziona, a kontakt udało się utrzymać aż do wynurzenia się B-36, kiedy zmuszony został do wypłynięcia na powierzchnię w skutek wyczerpania niepełnych już wcześniej akumulatorów oraz wyczerpania załogi[2]. Przez następne 36 godzin, radziecki okręt na powierzchni w obecności amerykańskiego niszczyciela ładował akumulatory, załoga zaś modyfikowała w tym czasie okrętowy holowany pozorator, dostosowując częstotliwość jego pracy do częstotliwości pracy sonaru amerykańskiego niszczyciela. Po zakończeniu tych czynności, B-36 zanurzył się i uruchomił pozorator. Skutkiem tego, \"Charles P. Cecil\" nie był w stanie ponownie odnaleźć radzieckiego okrętu, który odpłynął[2]."
                + "Trzecim okrętem radzieckim, który zmuszony był wynurzyć się, był B-130. Okręt ten został wykryty 24 października 420 mil na północ od Portoryko przez samolot patrolowy P5M Marlin, a następnie 30 października przez radar niszczyciela USS \"Blandy\" typu Forrest Sherman. Po zanurzeniu się okrętu podwodnego, w celu zapobieżenia wykryciu radziecka jednostka operowała 150 metrów poniżej termokliny. Po dołączeniu do \"Blandy'ego\" dwóch kolejnych niszczycieli, radziecki okręt zwiększył głębokość zanurzenia do 250 metrów oraz zastosował pułapki akustyczne w celu zmylenia operatorów amerykańskich sonarów. W rzeczywistości doprowadziło to do niebezpiecznej sytuacji, bowiem załogi amerykańskich okrętów początkowo wzięły generowany przez urządzenie dźwięk, za dźwięk wystrzelonej torpedy, skutkiem czego dowódca \"Blandy'ego\" wydał rozkaz przygotowania do strzału samonaprowadzającej się na cel torpedy przeciwpodwodnej i miotacz rakietowych bomb głębinowych Hedgehog[2]. Radziecki okręt miał jednak problemy techniczne z własnym systemem napędowym, co zmusiło jego dowódcę do wynurzenia jednostki na powierzchnię. Okręt nie był w stanie prowadzić dalej operacji i wezwał holownik, który odholował go następnie do bazy w Związku Radzieckim[2]. W połowie listopada, statki transportujące radzieckie pociski balistyczne na Kubę zostały zawrócone do ZSRR, a trzy operujące jeszcze w pobliżu Kuby Foxtroty otrzymały rozkaz powrotu do bazy."
                + "Radzieckie okręty podwodne próbowały unikać wykrycia przez siły amerykańskie przez stosowanie krótkich zrywów wysokiej prędkości, gwałtowne manewrowanie (włączając w to gwałtowne nagłe wstecz), wykorzystywanie termoklin, chowanie się pod śladem torowym jednostek nawodnych, wypuszczanie bąbli powietrza oraz pozoratorów akustycznych[1]. Ich dowódcy wiedzieli, że prawdopodobnie nie zostaną zaatakowani bronią bojową i nie byli sami zobowiązani do atakowania amerykańskich okrętów, toteż nie działali w warunkach realistycznej sytuacji konfliktowej. Mogli sobie zatem pozwolić na częste i długie przebywanie na powierzchni w celu ładowania akumulatorów i używanie radarów, co prawdopodobnie nie byłoby możliwe w realnych warunkach wojennych. Z tych powodów, zarówno doświadczenia radzieckie jak i amerykańskich sił ZOP podczas kryzysu kubańskiego, nie pozwalają na wyciąganie daleko idących wniosków co do użycia okrętów podwodnych w konflikcie morskim[1].";
        String s4 = s+s2+s3+s+s2+s3+s+s2+s3+s+s2+s3+s+s2+s3+s+s2+s3+s+s2+s3+s+s2+s3+s+s2+s3;
        
        
        byte[] b = s4.getBytes();
        
        return b;
    }
}


