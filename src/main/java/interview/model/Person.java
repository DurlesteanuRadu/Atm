package interview.model;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class Person {

    private int id;
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public Person() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person " + name;
    }

    public void useAtm(Atm atm){
        atm.start(this);
    }

    public Set<Card> ownedCards() {
        return null;
    }
}

