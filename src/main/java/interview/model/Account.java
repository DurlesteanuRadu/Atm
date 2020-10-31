package interview.model;


public class Account {
    private String owner;
    private String account_number;
    private String currency;
    private long credit;

    public Account() {
    }

    public Account(String owner, String account_number, String currency, long credit) {
        this.owner = owner;
        this.account_number = account_number;
        this.currency = currency;
        this.credit = credit;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
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
        return "Account " + account_number + " with the owner " + owner + " has " + credit + " " + currency;
    }
}
