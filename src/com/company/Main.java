package com.company;


import java.util.Random;
import java.util.Scanner;

public class Main {
    static Random rand = new Random(); // mēs izveidojām jaunu mainīgo vērtību rand kura ir Random tipa objekts,
    static Scanner scan = new Scanner(System.in); //mēs izveidojām jaunu skaneri, kas nolasa no formas (System.in) ko ievadīja lietotājs

    public static void main(String[] args) {
        int myNum = rand.nextInt(100) + 1; // bound 100 apzīmē diapazonu no 0 līdz 99. Ar +1 mēs nosakam diapazonu no 1 līdz 100
        System.out.println(myNum);

        boolean userLost = true; // mēs izveidojām šo mainīgo lai noskaidrotu vai lietotājs zaudeja vai vinnēja. vēlāk mēs šo mainīgo izmantosim lai atspoguļotu tekstu zaudējuma gadījumā.

        for (int attempt = 1; attempt <= 7; attempt++) { //ar šo ciklu mēs dodam lietotājam x mēģinājumus atminēt skaitli.
            System.out.println("Try #" + attempt); // tas ir priekš teksta pirms katra mēģinājuma,
            int userNum = scan.nextInt(); // ar šo mēs prasam programmu interpretēt to ko ievadīja lietotājs kā integer

            if (myNum > userNum) {
                System.out.println("My number is greater");
            } else if (myNum < userNum) {
                System.out.println("Your number is greater");
            } else if (myNum == userNum) { // šajā gadījumā šo if kombināciju var nerakstīt jo else pietiek tā kā 3.variants nav espējams
                System.out.println("You won!");
                userLost = false;
                break; // ar šo break mēs pasakam programmai ka jāapstājas ja lietotājs atmin skaitli. Break beidz ciklu.
            }
        }
        if (userLost == true) { // Izgaismots tāpēc ka mēs varam nerakstīt == true, jo userLost jau pašā sākumā noteikt kā true. ja userLost parādās šeit tas arī nozīmē ka tas ir true.
            System.out.println("You lost. Buagagagaga!!");
        }
        System.out.println("Would you like to play again? Please enter yes or no.");
    }
}

