package interview.model;


import interview.repository.CardRepository;

import java.util.ArrayList;
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

    public void start(Person p) {
        Scanner sc = new Scanner(System.in);
        Card c = new Card();
        boolean value;

        IntroductionMessages();

        value = InsertCard(sc, p, c);
        if (!value) {
            System.out.println("Have a nice day!");
            return;
        }

        value = ChoosingOptions(sc, c);
        if (!value) {
            System.out.println("Have a nice day!");
        }

    }

    private void IntroductionMessages() {
        System.out.println("\n\n----------------------------------------------------------");
        System.out.println("| Welcome to the ING's brand new ATM!");
        try {
            Thread.sleep(2000);
            System.out.println("| If you want to interrupt at any time, press 0!");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean InsertCard(Scanner sc, Person p, Card c) {
        CardRepository cardRepository = CardRepository.getInstance();
        ArrayList<Card> cards = cardRepository.getCardsByName(p.getName());
        int nr;
        String s;

        System.out.println("| Do you want to remember what cards do you have? (Y/N)");
        while (true) {
            System.out.print("| ");
            s = sc.nextLine();
            s = s.toUpperCase();

            if (s.equals("Y") || s.equals("YES")) {
                for (int i = 0; i < cards.size(); ++i)
                    System.out.println("| " + (i + 1) + ". " + cards.get(i));
                break;
            }

            if (s.equals("N") || s.equals("NO"))
                break;

            if (s.equals("0"))
                return false;
            System.out.println("| Type a valid answer! (Y/N)");
        }

        System.out.println("| Insert your card!");

        while (true) {
            System.out.print("| ");
            nr = sc.nextInt();
            if (nr == 0)
                return false;
            if (nr <= cards.size())
                break;
            else
                System.out.println("| Choose a valid card! You only have " + (cards.size()) + " cards!");
        }

        c = cards.get(nr - 1);

        System.out.println("| Enter your PIN!");
        int pin = -1;
        while (true) {
            System.out.print("| ");
            pin = sc.nextInt();
            if (pin == 0)
                return false;
            if (pin == c.getPin())
                break;
            else
                System.out.println("| Incorrect PIN! Type again!");
        }
        return true;
    }

    private boolean ChoosingOptions(Scanner sc, Card c) {
        System.out.println("| Choose your preferred option!");
        System.out.println("| 1. Withdraw cash       4. Deposit cash");
        System.out.println("| 2. Check your balance  0. Exit");
        System.out.println("| 3. Change your PIN   ");

        int nr;
        while (true) {
            System.out.print("| ");
            nr = sc.nextInt();
            if (0 <= nr && nr <= 4)
                break;
            else
                System.out.println("| Choose a valid option!");
            }
        switch (nr) {
            case 0:
                return false;
            case 1:
                WithdrawCash();
                break;
            case 2:
                CheckBalance();
                break;
            case 3:
                ChangePIN();
                break;
            case 4:
                DepositCash();
                break;
        }
        return true;
    }

    private void WithdrawCash() {
        System.out.println("1");
    }

    private void CheckBalance() {
        System.out.println("2");
    }

    private void ChangePIN() {
        System.out.println("3");
    }

    private void DepositCash() {
        System.out.println("4");
    }
}
