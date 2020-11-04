package interview.model;

import interview.log.LogService;

import java.util.Set;

public class Person {

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

    // Method that allows the person to start using the ATM
    public void useAtm(Atm atm){
        LogService logService = LogService.getInstance();
        logService.start();
        logService.record("Person " + name + " started using the ATM");

        atm.start(this);

        logService.record("Person " + name + " stopped using the ATM");
    }

    public Set<Card> ownedCards() {
        return null;
    }
}

