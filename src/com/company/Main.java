package com.company;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.*;

public class Main {
    static Random rand = new Random(); // mēs izveidojām jaunu mainīgo vērtību rand kura ir Random tipa objekts,
    static Scanner scan = new Scanner(System.in); //mēs izveidojām jaunu skaneri, kas nolasa no formas (System.in) ko ievadīja lietotājs
    static List<GameResult> results = new ArrayList<>(); // nazvanie lista sovpadaet s nazvaniem novogo klassa "GameResult"

    public static void main(String[] args) {

        loadResults(); // metod dlja zapuska rezultatov

        String answer; //šo mainīgo vajag iznest ārā, jo mainīgā vērtība dzīvo tikai iekš {}
        do {
            System.out.println("What is your name?");
            String userName = scan.next();
            System.out.println("Hi, " + userName + ", let's play!");

            int myNum = rand.nextInt(100) + 1; // bound 100 apzīmē diapazonu no 0 līdz 99. Ar +1 mēs nosakam diapazonu no 1 līdz 100
            System.out.println(myNum); // ja aktivizēt šo, tad IDEA rādīs skaitli kuru prasa programma

            long t1 = System.currentTimeMillis();

            boolean userLost = true; // mēs izveidojām šo mainīgo lai noskaidrotu vai lietotājs zaudeja vai vinnēja. vēlāk mēs šo mainīgo izmantosim lai atspoguļotu tekstu zaudējuma gadījumā.

            for (int attempt = 1; attempt <= 7; attempt++) { //ar šo ciklu mēs dodam lietotājam x mēģinājumus atminēt skaitli.
                System.out.println("Try #" + attempt); // tas ir priekš teksta pirms katra mēģinājuma,
                int userNum = askNum(); // ar šo mēs prasam programmu interpretēt to ko ievadīja lietotājs kā integer

                if (myNum > userNum) {
                    System.out.println("My number is greater");
                } else if (myNum < userNum) {
                    System.out.println("Your number is greater");
                } else if (myNum == userNum) { // šajā gadījumā šo if kombināciju var nerakstīt jo else pietiek tā kā 3.variants nav espējams
                    System.out.println("You won!");
                    System.out.println("Would you like to play again? Please enter yes or no.");
                    userLost = false;

                    long t2 = System.currentTimeMillis();

                    long userPlayTime = (t2 - t1); // poluchennye mili sekundy delim na 1000 i poluchili sekundy

                    GameResult r = new GameResult(); // sozdaem novyj rezultat dlaj peremennogo znachenija i etot rezultat budet hranitsja v peremennoj r
                    r.name = userName; // .name berem iz GameResult
                    r.triesCount = attempt; // i = kol-vo popytok.
                    r.gameTime = userPlayTime;
                    results.add(r);

                    results.sort(Comparator.<GameResult>comparingInt(r0 -> r0.triesCount).thenComparingLong(r0 -> r0.gameTime)); // šīs ieraksts maina SARAKSTU FAILĀ, sasortējot to pēc triesCount. katru reizi kad beigsies spēle, saraksts tiks sasortēts no jauna
// result.sort sasortē visu sarakstu ar rezultātierm pēc katras spēles beogām.
                    break;
                }
            }
            if (userLost == true) { // Izgaismots tāpēc ka mēs varam nerakstīt == true, jo userLost jau pašā sākumā noteikt kā true. ja userLost parādās šeit tas arī nozīmē ka tas ir true.
                System.out.println("You lost. Buagagagaga!!");
                System.out.println("Would you like to play again? Please enter yes or no.");
            }
            answer = askYN(); // kad programma nonāks līdz šai vietai, tā aizies uz static String askNY, lai pajautātu

        } while (answer.equalsIgnoreCase("yes")); // do ends here

        showResult(); // vyzyvaem metod. Dlja pokazyvanija vseh rezultatov

        saveResults(); // izveidojām metodi, lai varētu saglabāt spēles rezultātus uz diska un nākamreiz tie būtu pieejami.

        System.out.println("Good bye!"); // šo tekstu paska ja lietotājs negrib turpināt.
    }

    private static void loadResults() {
        File file = new File("top_scores.txt");
        try (Scanner in = new Scanner(file)) {

            while (in.hasNext()) { // .hasNext pārbauda vai failā ir vēl kaut kas ko var izlasīt
                GameResult result = new GameResult();
                result.name = in.next();
                result.triesCount = in.nextInt();
                result.gameTime = in.nextLong();
                results.add(result);
            }
        } catch (IOException e) {
            System.out.println("Cannot load from file");
        }
    }

    private static void saveResults() {
        File file = new File("top_scores.txt"); // ja nenorādīt konkrēetu adresi, IDEA saglabās šo failu projekta direktorijā
        try (PrintWriter out = new PrintWriter(file)) {
            for (GameResult r : results) {
                out.printf("%s %d %d\r\n", r.name, r.triesCount, r.gameTime); // lietojām printf (rakstīt ar formatēšanu; \r - lai ieraksti būtu katrs savā rindā notepad failā. tas ir vajadzīgs tikai windows. IDEA to lasa normāli. \n - znak dlja perevoda stroki)
            }
        } catch (IOException e) {
            System.out.println("Cannot save to file");
        }
    }
// 1. variants kā rādīt tikai X rezultātus

//    private static void showResult() { // jeto stroku mozhno napisatj manualno ili nazatj ALT + ENTER na showResult
//        int count = 0;
//        for (GameResult r : results) { // nuzhno vzjatj spisok result i projtisj po vsem peremennym
//            System.out.printf("%s needed %d tries and %.2fsec to win\n", r.name, r.triesCount, (r.gameTime / 1000.0));
//
//            count ++;
//            if (count == 5) {
//                break;
//            }
//        }
//    }

// 2. variants kā rādīt X rezultātus

//    private static void showResult() { // jeto stroku mozhno napisatj manualno ili nazatj ALT + ENTER na showResult
//        int count = 5;
//        if (results.size() < 5) {
//            count = results.size();
//        }
//        for (int i = 0; i < count; i++) {
//            GameResult r = results.get(i); // nuzhno vzjatj spisok result i projtisj po vsem peremennym
//            System.out.printf("%s needed %d tries and %.2fsec to win\n", r.name, r.triesCount, (r.gameTime / 1000.0));
//        }
//    }

// 3 variants kā rādīt X rezultātus

//    private static void showResult() { // jeto stroku mozhno napisatj manualno ili nazatj ALT + ENTER na showResult
//        int count = Math.min(5, results.size());
//        for (int i = 0; i < count; i++) {
//            GameResult r = results.get(i); // nuzhno vzjatj spisok result i projtisj po vsem peremennym
//            System.out.printf("%s needed %d tries and %.2fsec to win\n", r.name, r.triesCount, (r.gameTime / 1000.0));
//        }
//    }

    // 4. variants kā rādīt X rezultātus
    private static void showResult() { // jeto stroku mozhno napisatj manualno ili nazatj ALT + ENTER na showResult

        int maxLen = findMaxNameLen();

        results.stream() // ņemam spisok rezult un pārveidojam to uz Stream
                .limit(5) // rādīs tikai 5 rezultātus
                .forEach(r -> {
                    System.out.print(r.name);
                    for (int i = 0; i < (maxLen - r.name.length()); i++) {
                        System.out.print("_");
                    }
                    System.out.printf(" needed %d tries and %.2fsec to win\n", r.triesCount, (r.gameTime / 1000.0));
                }); // curly brackets in brackets - ir lambda. peredajetsja dejstvie.
    }

// 1. TABLICA
//    private static int findMaxNameLen() {
//        int result = 0;
//        for (GameResult r : results) {
//            if (result < r.name.length()) {
//                result = r.name.length();
//            }
//        }
//        return result;
//    }

// 2. TABLICA
    private static int findMaxNameLen() {
        return results.stream()
                .map(r -> r.name)
                .map(n -> n.length())
                .max(Comparator.naturalOrder())
                .get();
    }

    // šī daļa ir pēc Main metodes.
    static String askYN() {  // izveidojām metodi, kurš jautās lietotājam yes vai No. šo metodi mēs varam labot un tas neietekmēs citas rindas.
        String answer;
        do {
            answer = scan.next();
            if (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
                System.out.println("You can enter only yes or no");
                continue; // kad nonāks līdz continue, programmai jāsāk cils no sākuma. šajā gadījumā no do{}.
            } else {
                return answer; // break ir vajadzīgs lai beigtu bezgalīgu while true. ja lietotājs ievadīs yes vai no, tad nostrādās break. šajā gadījumā break vietā ir return aNSWER.
            }
        } while (true); //while true - beskonechnij cikl. poetomu pered etim u nas Break.
    }


// * Izveidojām jaunu metodi, lai jautātu veselu skaitli no 0 līdz 100. ja lietotājs ievadīs kaut ko citu, programma paziņos.
// * ar šo mēs mainām sākotnējo kodu.

    static int askNum() {
        int guessNum;
        do {
            try {
                guessNum = scan.nextInt();
            } catch (InputMismatchException e) { // ieviesām junu mainīgo "e". informācija par kļūdu iekļūst mainīgajā "e"
                System.out.println("This is not a number");
                scan.next(); // šī rinda neļauj rādīt "This is not a number" bezgalīgi ilgi un turpina gaidīt nākamo lietotāja ierakstu.
                continue;
            }
            if (guessNum > 100 || guessNum < 1) {
                System.out.println("You can only insert an integer between 1 and 100");
            } else {
                return guessNum; // šī rinda atgiež lietotāja ievadito atbildi kodā
            }
        } while (true);
    }
}