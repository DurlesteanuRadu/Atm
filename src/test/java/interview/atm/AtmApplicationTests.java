package interview.atm;

import interview.model.Atm;
import interview.model.Person;
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
	private ByteArrayOutputStream baos;
	private PrintStream ps, old;

	@BeforeEach
	void setUp() {
		AtmApplication.AddInfoToRepositories();
		PersonRepository personRepository = PersonRepository.getInstance();
		p = personRepository.getPersonByName("Anna");
		a = Atm.getInstance();

		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		old = System.out;
		System.setOut(ps);
	}

	@AfterEach
	void tearDown() {

	}

	@Test
	void InsertCardTest() {
		String data = "nO\r\n1\r\n1235\r\n1234\r\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));

		sc = new Scanner(System.in);
		a.InsertCard(sc, p);

		System.out.flush();
		System.setOut(old);

		System.out.println("***Output: " + baos.toString());
	}
}
