package interview.model;


import interview.repository.AccountRepository;
import interview.repository.CardRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class Atm {

    private static Atm instance;
    private static Card card;

    public Atm() {
    }

    public static Atm getInstance() {
        if (instance == null)
            instance = new Atm();

        return instance;
    }

    public void start(Person p) {
        Scanner sc = new Scanner(System.in);
        boolean value;
        int nr = 0;

        IntroductionMessages();

        value = InsertCard(sc, p);
        if (!value) {
            System.out.println("| Have a nice day!");
            return;
        }


        value = ChoosingOptions(sc);
        if (!value) {
            System.out.println("| Have a nice day!");
        }

        System.out.println("| Have a nice day!");

    }

    private void IntroductionMessages() {
        System.out.println("\n\n-------------------------------------------------------");
        System.out.println("| Welcome to the ING's brand new ATM!");
        try {
            Thread.sleep(2000);
            System.out.println("| If you want to interrupt at any time, press 0!");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean InsertCard(Scanner sc, Person p) {
        CardRepository cardRepository = CardRepository.getInstance();
        AccountRepository accountRepository = AccountRepository.getInstance();
        ArrayList<Card> cards = cardRepository.getCardsByName(p.getName());

        String s;
        int nr;

        System.out.println("| Do you want to remember what cards do you have? (Y/N)");
        while (true) {
            System.out.print("| ");
            s = sc.nextLine();
            s = s.toUpperCase();

            if (s.equals("Y") || s.equals("YES")) {
                for (int i = 0; i < cards.size(); ++i) {
                    String cardNumber = cards.get(i).getCardNumber();
                    Account a = accountRepository.getAccountByNumber(cardNumber);
                    System.out.println("| " + (i + 1) + ". " + cards.get(i)
                            + " linked to the account in " + a.getCurrency());
                }
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
        Atm.card = cards.get(nr-1);

        System.out.println("| Enter your PIN!");
        String pin = "";
        while (true) {
            System.out.print("| ");
            sc.nextLine();
            pin = sc.nextLine();
            if (pin.equals("0"))
                return false;
            if (pin.equals(Atm.card.getPin()))
                break;
            else
                System.out.println("| Incorrect PIN! Type again!");
        }
        return true;
    }

    private boolean ChoosingOptions(Scanner sc) {
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
                WithdrawCash(sc);
                break;
            case 2:
                CheckBalance();
                break;
            case 3:
                ChangePIN(sc);
                break;
            case 4:
                DepositCash(sc);
                break;
        }
        return true;
    }

    private boolean WithdrawCash(Scanner sc) {
        AccountRepository accountRepository = AccountRepository.getInstance();

        int[] sums = {0, 10, 50, 100, 250, 500, 1000};
        Account a = accountRepository.getAccountByNumber(Atm.card.getCardNumber());
        int credit = a.getCredit();

        System.out.println("| Choose what sum you want to withdraw!");
        System.out.println("| 1. 10   5. 500");
        System.out.println("| 2. 50   6. 1000");
        System.out.println("| 3. 100  7. Another sum");
        System.out.println("| 4. 250  0. Exit");

        int nr;
        while (true) {
            System.out.print("| ");
            nr = sc.nextInt();
            if (nr == 7)
                break;
            if (nr == 0)
                return false;
            if (1 <= nr && nr <= 6)
                if (credit >= sums[nr]) {
                    a.setCredit(credit - sums[nr]);
                    System.out.println("| Please wait while we process your transaction!");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                }
                else
                    System.out.println("| You do not have enough credit!");
            else
                System.out.println("| Choose a valid option!");
        }

        if (nr == 7) {
            System.out.println("| Enter the amount you want to withdraw! (Multiple of 10)");
            while (true) {
                System.out.print("| ");
                nr = sc.nextInt();
                if (nr % 10 == 0)
                    if (credit >= nr) {
                        a.setCredit(credit - nr);
                        System.out.println("| Please wait while we process your transaction!");
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    else
                        System.out.println("| You do not have enough credit!");
                else
                    System.out.println("| You need to enter a sum multiple of 10!");
            }
        }


        return true;

    }

    private boolean CheckBalance() {
        AccountRepository accountRepository = AccountRepository.getInstance();
        Account account = accountRepository.getAccountByNumber(Atm.card.getCardNumber());

        System.out.println("| Your Balance: " + account.getCredit() + account.getCurrency());
        return true;
    }

    private boolean ChangePIN(Scanner sc) {
        String pin = "";

        System.out.println("| Enter your old PIN!");
        while (true) {
            System.out.print("| ");
            sc.nextLine();
            pin = sc.nextLine();
            if (pin.equals("0"))
                return false;
            if (pin.equals(Atm.card.getPin()))
                break;
            else
                System.out.println("| Incorrect PIN! Type again!");
        }

        System.out.println("| Enter your new PIN!");
        while (true) {
            System.out.print("| ");
            pin = sc.nextLine();
            if (pin.equals("0"))
                return false;
            if (pin.length() == 4) {
                System.out.println("| Your new PIN has been set!");
                break;
            }
            else
                System.out.println("| Invalid PIN! Type again!");
        }

        return true;
    }

    private boolean DepositCash(Scanner sc) {
        AccountRepository accountRepository = AccountRepository.getInstance();
        Account account = accountRepository.getAccountByNumber(Atm.card.getCardNumber());
        int nr;

        System.out.println("| Enter the amount of credit you deposit!");
        System.out.println("| ");
        nr = sc.nextInt();
        if (nr == 0)
            return false;

        account.setCredit(account.getCredit() + nr);

        return true;
    }
}
