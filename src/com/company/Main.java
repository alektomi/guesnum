package com.company;


import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Random rand = new Random(); // mēs izveidojām jaunu mainīgo vērtību rand kura ir Random tipa objekts,
    static Scanner scan = new Scanner(System.in); //mēs izveidojām jaunu skaneri, kas nolasa no formas (System.in) ko ievadīja lietotājs

    public static void main(String[] args) {

        String answer; //šo mainīgo vajag iznest ārā, jo mainīgā vērtība dzīvo tikai iekš {}

        do {
            int myNum = rand.nextInt(100) + 1; // bound 100 apzīmē diapazonu no 0 līdz 99. Ar +1 mēs nosakam diapazonu no 1 līdz 100
            System.out.println(myNum);

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
                    break;
                }
            }
            if (userLost == true) { // Izgaismots tāpēc ka mēs varam nerakstīt == true, jo userLost jau pašā sākumā noteikt kā true. ja userLost parādās šeit tas arī nozīmē ka tas ir true.
                System.out.println("You lost. Buagagagaga!!");
                System.out.println("Would you like to play again? Please enter yes or no.");
            }
            answer = askYN(); // kad programma nonāks līdz šai vietai, tā aizies uz static String askNY, lai pajautātu

        } while (answer.equalsIgnoreCase("yes")); // do ends here
        System.out.println("Good bye!"); // šo tekstu paska ja lietotājs negrib turpināt.
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