package interview.model;


public class Account {
    private String owner;
    private String accountNumber;
    private String currency;
    private long credit;

    public Account() {
    }

    public Account(String owner, String accountNumber, String currency, long credit) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.credit = credit;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String account_number) {
        this.accountNumber = account_number;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Account " + accountNumber + " with the owner " + owner + " has " + credit + " " + currency;
    }
}
