package interview.model;


public class Card {
    
    private String name;
    private String cardNumber;
    private String pin;

    public Card() { }

    public Card(String name, String cardNumber, String pin) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.pin = pin;
    }
    public void Copy(Card c) {
        this.name = c.name;
        this.cardNumber = c.cardNumber;
        this.pin = c.pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String card_number) {
        this.cardNumber = card_number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Card " + cardNumber;
    }
}
