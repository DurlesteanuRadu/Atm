package interview.model;


import interview.repository.*;
import interview.log.LogService;
import java.util.List;
import java.util.Scanner;

public class Atm {

    private static Atm instance;
    private static Card card;

    public Atm() {
    }

    public static void setCard(Card card) {
        Atm.card = card;
    }

    public static Atm getInstance() {
        if (instance == null)
            instance = new Atm();

        return instance;
    }

    // Main method of Atm class, person starts to use the ATM
    public void start(Person p) {
        Scanner sc = new Scanner(System.in);

        IntroductionMessages();

        if (!InsertCard(sc, p)) {
            System.out.println("| Have a nice day!");
            return;
        }

        ChoosingOptions(sc);

        System.out.println("| Have a nice day!");

    }

    private void IntroductionMessages() {
        System.out.println("\n\n-------------------------------------------------------");
        System.out.println("| Welcome to the ING's brand new ATM!");

        // Small delay that allows the user to have time to read information

        try {
            Thread.sleep(2000);
            System.out.println("| If you want to interrupt at any time, press 0!");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method that allows the person to see what cards does he have, choose which card to insert into the ATM
    // And enter the PIN for the selected card
    // The method return false if at any time, the person has entered the exit button, 0
    // True, otherwise, so the process can continue
    public boolean InsertCard(Scanner sc, Person p) {
        CardRepository cardRepository = CardRepository.getInstance();
        AccountRepository accountRepository = AccountRepository.getInstance();
        List<Card> cards = cardRepository.getCardsByName(p.getName());
        LogService logService = LogService.getInstance();

        String s;
        int nr;

        // Give the person the opportunity to check what cards does he have
        // The cards not being physical, the person may not know what cards he has
        System.out.println("| Do you want to check what cards do you have? (Y/N)");
        // Infinite cycle, exited only by a valid answer or by 0
        
        while (true) {
            // Processing the input
            System.out.print("| ");
            s = sc.nextLine();
            // When the first input is read, the string = "". This is caused by the output formatting 
            if (s.equals(""))
                s = sc.nextLine();
            s = s.toUpperCase();

            // Printing all the cards and exiting the cycle
            if (s.equals("Y") || s.equals("YES")) {
                for (int i = 0; i < cards.size(); ++i) {
                    // cardNumber = accountNumber
                    // Retrieving both from Repositories allows for more info to be displayed
                    String cardNumber = cards.get(i).getCardNumber();
                    Account a = accountRepository.getAccountByNumber(cardNumber);
                    System.out.println("| " + (i + 1) + ". " + cards.get(i)
                            + " linked to the account in " + a.getCurrency());
                }
                break;
            }
            
            // No cards displayed, exiting the cycle
            if (s.equals("N") || s.equals("NO"))
                break;
            
            // Person wants to leave the ATM
            if (s.equals("0"))
                return false;
            
            System.out.println("| Type a valid answer! (Y/N)");
        }
        
        // The person is asked the insert the desired card
        System.out.println("| Insert your card! (type index of your card)");
        // Infinite cycle, exited only by a valid answer or by 0
        
        while (true) {
            // Processing the input
            System.out.print("| ");
            nr = sc.nextInt();
            
            // Person wants to leave the ATM
            if (nr == 0)
                return false;
            
            // Valid card, exiting the cycle
            if (nr <= cards.size())
                break;
            else
                System.out.println("| Choose a valid card! You only have " + (cards.size()) + " cards!");
        }
        // Cards are 0-indexed, person types 1-indexed
        Atm.card = cards.get(nr-1);

        logService.record("Person " + p.getName() + " inserted Card " + Atm.card.getCardNumber());

        System.out.println("| Enter your PIN!");
        String pin;
        // The person only has 3 tries to enter the PIN, after that, the card is detained.
        int tries = 3;
        
        while (tries > 0) {
            System.out.print("| ");
            pin = sc.nextLine();
            // When the first input is read, the string = "". This is caused by the output formatting 
            if (pin.equals(""))
                pin = sc.nextLine();
            
            // Person wants to leave the ATM
            if (pin.equals("0"))
                return false;
            
            // Right PIN
            if (pin.equals(Atm.card.getPin()))
                break;
            else {
                // Wrong PIN, one less try
                tries--;
                System.out.println("| Incorrect PIN! You have " + tries + " tries left!");
                // If no more tries, detain card and delete it from the Repository
                if (tries == 0) {
                    System.out.println("| Your card has been detained!");
                    cardRepository.deleteCard(Atm.card);

                    logService.record("Detained Card " + Atm.card.getCardNumber());

                    return false;
                }
            }
        }
        return true;
    }
    
    // Method that allows the person to continue using the ATM
    // Displays the options he has
    // The method return false if at any time, the person has entered the exit button, 0
    // True, otherwise, so the process can continue
    private void ChoosingOptions(Scanner sc) {
        
        // Infinite cycle, exited only when the person doesn't want to do any more operations
        while (true) {
            System.out.println("| Choose your preferred option!");
            System.out.println("| 1. Withdraw cash       4. Deposit cash");
            System.out.println("| 2. Check your balance  0. Exit");
            System.out.println("| 3. Change your PIN   ");
            
            int nr;
            boolean value = true;
            String s;
            // Infinite cycle, exited only when the person has chosen a valid option
            while (true) {
                System.out.print("| ");
                nr = sc.nextInt();
                if (0 <= nr && nr <= 4)
                    break;
                else
                    System.out.println("| Choose a valid option!");
            }
            
            switch (nr) {
                // The person wants to leave the ATM
                case 0:
                    return;
                // value gives information on how the operation ended
                // true = successfully
                // false = the person entered the exit button, 0
                case 1:
                    value = WithdrawCash(sc);
                    break;
                case 2:
                    value = CheckBalance();
                    break;
                case 3:
                    value = ChangePIN(sc);
                    break;
                case 4:
                    value = DepositCash(sc);
                    break;
            }
            if (!value)
                return;
            else {
                // Asking the person if he wants to continue using the ATM
                System.out.println("| Would you like another service? (Y/N)");

                // Infinite cycle, exited only by a valid answer or by 0
                while (true) {
                    System.out.print("| ");
                    s = sc.nextLine();
                    // When the first input is read, the string = "". This is caused by the output formatting 
                    if (s.equals(""))
                        s = sc.nextLine();
                    s = s.toUpperCase();
                    
                    // The person wants to do another operation
                    if (s.equals("Y") || s.equals("YES")) {
                        break;
                    }
                    
                    // The person want to leave the ATM.
                    if (s.equals("N") || s.equals("NO") || s.equals("0"))
                        return;
                    
                    System.out.println("| Type a valid answer! (Y/N)");
                }
            }
        }

    }

    // Method that allows the person to withdraw cash
    // The method return false if at any time, the person has entered the exit button, 0
    // True, otherwise, so the process can continue

    public boolean WithdrawCash(Scanner sc) {
        AccountRepository accountRepository = AccountRepository.getInstance();

        // All the possible sums to withdraw
        int[] sums = {0, 10, 50, 100, 250, 500, 1000};
        Account account = accountRepository.getAccountByNumber(Atm.card.getCardNumber());
        int credit = account.getCredit();

        System.out.println("| Choose what sum you want to withdraw!");
        System.out.println("| 1. 10   5. 500");
        System.out.println("| 2. 50   6. 1000");
        System.out.println("| 3. 100  7. Another sum");
        System.out.println("| 4. 250  0. Exit");

        int nr;
        // Infinite cycle, exited only by a valid answer or by 0
        while (true) {
            System.out.print("| ");
            nr = sc.nextInt();

            // Custom withdrawal sum
            if (nr == 7)
                break;

            // The person wants to leave the ATM
            if (nr == 0)
                return false;

            // 1-6 are predetermined withdrawal sums. If the person has enough credit, withdraw cash
            if (1 <= nr && nr <= 6)
                if (credit >= sums[nr]) {
                    account.setCredit(credit - sums[nr]);
                    System.out.println("| Please wait while we process your transaction!");

                    LogService logService = LogService.getInstance();
                    logService.record("Withdrawn " + sums[nr] + account.getCurrency()
                            + " from Card " + Atm.card.getCardNumber());

                    // Delay for realism
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

        // The person wants to withdraw a custom sum
        if (nr == 7) {
            System.out.println("| Enter the amount you want to withdraw! (Multiple of 10)");

            // Infinite cycle, exited only by a valid answer or by 0
            while (true) {
                System.out.print("| ");
                nr = sc.nextInt();

                // The person wants to leave the ATM
                if (nr == 0)
                    return false;

                if (nr % 10 == 0)
                    if (credit >= nr) {
                        account.setCredit(credit - nr);
                        System.out.println("| Please wait while we process your transaction!");

                        LogService logService = LogService.getInstance();
                        logService.record("Withdrawn " + nr + account.getCurrency()
                                + " from Card" + Atm.card.getCardNumber());

                        // Delay for realism
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

    // Method that prints the balance of the account linked to the card
    public boolean CheckBalance() {
        AccountRepository accountRepository = AccountRepository.getInstance();
        Account account = accountRepository.getAccountByNumber(Atm.card.getCardNumber());
        LogService logService = LogService.getInstance();

        System.out.println("| Your Balance: " + account.getCredit() + account.getCurrency());

        logService.record("Checked balance of Card " + Atm.card.getCardNumber());
        return true;
    }

    // Method that allows the person to change the PIN
    // The method return false if at any time, the person has entered the exit button, 0
    // True, otherwise, so the process can continue
    public boolean ChangePIN(Scanner sc) {
        String pin;
        CardRepository cardRepository = CardRepository.getInstance();
        LogService logService = LogService.getInstance();

        System.out.println("| Enter your old PIN!");

        // The person only has 3 tries to enter the PIN, after that, the card is detained.
        int tries = 3;
        while (tries > 0) {
            System.out.print("| ");
            pin = sc.nextLine();

            // When the first input is read, the string = "". This is caused by the output formatting
            if (pin.equals(""))
                pin = sc.nextLine();

            // Person wants to leave the ATM
            if (pin.equals("0"))
                return false;

            // Right PIN
            if (pin.equals(Atm.card.getPin()))
                break;
            else {
                // Wrong PIN, one less try
                tries--;
                System.out.println("| Incorrect PIN! You have " + tries + " tries left!");

                // If no more tries, detain card and delete it from the Repository
                if (tries == 0) {
                    System.out.println("| Your card has been retained!");
                    cardRepository.deleteCard(Atm.card);

                    logService.record("Detained Card " + Atm.card.getCardNumber());

                    return false;
                }
            }
        }

        System.out.println("| Enter your new PIN!");

        // Infinite cycle, exited only by a valid answer or by 0
        while (true) {
            System.out.print("| ");
            pin = sc.nextLine();

            // The person want to leave the ATM
            if (pin.equals("0"))
                return false;

            if (pin.equals(Atm.card.getPin()))
                System.out.println("| New PIN can't be the same as old PIN!");
            else
                // If the new PIN has 4 digits, remember it
                if (pin.length() == 4 && pin.chars().allMatch( Character::isDigit )) {
                    System.out.println("| Your new PIN has been set!");
                    Atm.card.setPin(pin);

                    logService.record("Changed PIN of Card " + Atm.card.getCardNumber());

                    break;
                }
                else
                    System.out.println("| Invalid PIN! Type again! (It has to have 4 digits)");
        }

        return true;
    }

    // Method that allows the person to deposit cash
    public boolean DepositCash(Scanner sc) {
        AccountRepository accountRepository = AccountRepository.getInstance();
        Account account = accountRepository.getAccountByNumber(Atm.card.getCardNumber());
        LogService logService = LogService.getInstance();

        int nr;

        System.out.println("| Enter the amount of credit you deposit!");
        System.out.print("| ");
        nr = sc.nextInt();

        // The person wants to leave the ATM
        if (nr == 0)
            return false;

        account.setCredit(account.getCredit() + nr);

        System.out.println("| Your credit has been added to the account!");

        logService.record("Deposited " + nr + account.getCurrency() + " to Card " + Atm.card.getCardNumber());
        return true;
    }
}
