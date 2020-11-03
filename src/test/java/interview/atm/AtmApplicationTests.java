package interview.atm;

import interview.model.Atm;
import interview.model.Person;
import interview.repository.CardRepository;
import interview.repository.PersonRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@SpringBootTest
class AtmApplicationTests {

	private Person p;
	private Atm a;
	private Scanner sc;
	private ByteArrayOutputStream outputStream;
	private PrintStream old;

	@BeforeEach
	void setUp() {
		AtmApplication.AddInfoToRepositories();
		PersonRepository personRepository = PersonRepository.getInstance();
		p = personRepository.getPersonByName("Anna");
		a = Atm.getInstance();

		outputStream = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(outputStream);
		old = System.out;
		System.setOut(ps);
	}

	@Test
	void InsertCardRightPINTest() {
		int cardIndex = 1;
		String data = "nO\r\n" + cardIndex + "\r\n";
		data += CardRepository.getInstance().getCardsByName(p.getName()).get(cardIndex-1).getPin() + "\r\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		sc = new Scanner(System.in);

		boolean result = a.InsertCard(sc, p);
		assert result;

		System.out.flush();
		System.setOut(old);

		System.out.println("***Output: " + outputStream.toString());
	}

	@Test
	void InsertCardWrongPINTest() {
		int cardIndex = 1;
		String pin = CardRepository.getInstance().getCardsByName(p.getName()).get(cardIndex-1).getPin();
		pin = pin.substring(0, 2) + "*";

		String data = "nO\r\n" + cardIndex + "\r\n";
		data += pin + "\r\n" + pin + "\r\n" + pin;
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		sc = new Scanner(System.in);

		boolean result = a.InsertCard(sc, p);
		assert !result;

		System.out.flush();
		System.setOut(old);

		System.out.println("***Output: " + outputStream.toString());
	}
}
