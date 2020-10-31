package interview.repository;

import interview.model.Card;

import java.util.ArrayList;

public class CardRepository {

    private static CardRepository instance;
    private static ArrayList<Card> cards;

    public CardRepository() {
        cards = new ArrayList<Card>();
    }

    public static CardRepository getInstance() {
        if (instance == null)
            instance = new CardRepository();

        return instance;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Card> getCardsByName(String name) {
        ArrayList<Card> result = new ArrayList<>();

        for (Card c : cards) {
            if (c.getName().equals(name)) {
                result.add(c);
            }
        }
        return result;
    }

    public Card getCardByNumber(String number) {
        for (Card c : cards) {
            if (c.getCardNumber().equals(number))
                return c;
        }

        return null;
    }

    public void addCard(Card c) {
        cards.add(c);
    }
}
