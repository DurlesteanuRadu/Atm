package interview.repository;

import interview.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    private static PersonRepository instance;
    private static List<Person> persons;

    public PersonRepository() {
        persons = new ArrayList<Person>();
    }

    public static PersonRepository getInstance() {
        if (instance == null)
            instance = new PersonRepository();

        return instance;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public Person getPersonByName(String name) {
        for (Person p : persons) {
            if (p.getName().equals(name))
                return p;
        }

        return null;
    }

    public void addPerson(String name) {
        Person p = new Person(name);
        persons.add(p);
    }
}
