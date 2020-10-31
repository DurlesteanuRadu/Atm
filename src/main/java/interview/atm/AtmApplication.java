package interview.atm;

import interview.model.Account;
import interview.model.Atm;
import interview.model.Card;
import interview.model.Person;
import interview.repository.AccountRepository;
import interview.repository.CardRepository;
import interview.repository.PersonRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AtmApplication {

	public static void main(String[] args){
		
		SpringApplication.run(AtmApplication.class, args);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AddInfoToRepositories();
		PersonRepository personRepository = PersonRepository.getInstance();

		Person p = personRepository.getPersonByName("Anna");
		Atm a = Atm.getInstance();
		
		p.useAtm(a);
		}
		
	public static void AddInfoToRepositories() {
		
		PersonRepository personRepository = PersonRepository.getInstance();
		CardRepository cardRepository = CardRepository.getInstance();
		AccountRepository accountRepository = AccountRepository.getInstance();
		
		personRepository.addPerson("Anna");
		personRepository.addPerson("Bob");

		cardRepository.addCard(new Card("Anna", "111111", 1234));
		cardRepository.addCard(new Card("Anna", "222222", 1234));
		cardRepository.addCard(new Card("Bob", "333333", 1234));
		cardRepository.addCard(new Card("Bob", "444444", 1234));
		
		accountRepository.addAccount(new Account("Anna", "111111", "RON", 1000));
		accountRepository.addAccount(new Account("Anna", "222222", "USD", 100));
		accountRepository.addAccount(new Account("Bob", "111111", "RON", 2000));
		accountRepository.addAccount(new Account("Bob", "111111", "EUR", 50));
		
	}

}
