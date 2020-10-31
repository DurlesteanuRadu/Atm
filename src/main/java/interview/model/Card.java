package interview.model;


public class Card {


    private String name;
    private long card_number;
    private int cvv;
    private int pin;

    public Card() { }

    public Card(String name, long card_number, int cvv, int pin) {
        this.name = name;
        this.card_number = card_number;
        this.cvv = cvv;
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCard_number() {
        return card_number;
    }

    public void setCard_number(long card_number) {
        this.card_number = card_number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Card " + card_number + " has the owner " + name + " has the cvv " + cvv;
    }
}
