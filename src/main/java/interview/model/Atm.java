package interview.model;


import java.util.Scanner;

public class Atm {

    private static Atm instance;

    public Atm() {
    }

    public static Atm getInstance() {
        if (instance == null)
            instance = new Atm();

        return instance;
    }

    public void start(Person P) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Insert your card!");
        int nr = scan.nextInt();

        Card c = new Card("Radu", "12345", 1234);

        System.out.println("Enter your PIN!");
        int pin = -1;
        while (true)
        {
            pin = scan.nextInt();
            if (pin == c.getPin())
                break;
            else
                System.out.println("Incorrect PIN! Type again!");
        }

        System.out.println("Choose your preferred option!");
        System.out.println("1. Withdraw cash       4. Deposit cash");
        System.out.println("2. Check your balance  5. Exit");
        System.out.println("3. Change your PIN   ");
    }

    public void IntroductionMessages() {
        System.out.println("Welcome to the ING's brand new ATM!");
        try {
            Thread.sleep(2000);
            System.out.println("If you want to interrupt at any time, press 0!");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
