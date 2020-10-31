package interview.repository;

import interview.model.Account;
import java.util.ArrayList;

public class AccountRepository {

    private static AccountRepository instance;
    private static ArrayList<Account> accounts;

    public AccountRepository() {
        accounts = new ArrayList<Account>();
    }

    public static AccountRepository getInstance() {
        if (instance == null)
            instance = new AccountRepository();

        return instance;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Account> getAccountsByOwner(String name) {
        ArrayList<Account> result = new ArrayList<>();

        for (Account a : accounts) {
            if (a.getOwner().equals(name)) {
                result.add(a);
            }
        }
        return result;
    }

    public Account getAccountByNumber(String number) {
        for (Account a : accounts) {
            if (a.getAccountNumber().equals(number))
                return a;
        }

        return null;
    }

    public void addAccount(Account a) {
        accounts.add(a);
    }
}
