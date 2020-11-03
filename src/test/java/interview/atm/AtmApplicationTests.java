package interview.atm;

import interview.model.*;
import interview.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

@SpringBootTest
class AtmApplicationTests {

	private Person person;
	private Atm atm;
	private Scanner sc;
	private Card card;
	private int cardIndex = 1;
	private Account account;
	private ByteArrayOutputStream outputStream;
	private PrintStream old;


	@BeforeEach
	void setUp() {
		AtmApplication.AddInfoToRepositories();
		PersonRepository personRepository = PersonRepository.getInstance();
		person = personRepository.getPersonByName("Anna");
		atm = Atm.getInstance();
		card = CardRepository.getInstance().getCardsByName(person.getName()).get(cardIndex-1);
		account = AccountRepository.getInstance().getAccountByNumber(card.getCardNumber());
		Atm.setCard(card);
		outputStream = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(outputStream);
		old = System.out;
		System.setOut(ps);
	}

	@Test
	void InsertCardRightPINTest() {
		String data = "No\r\n" + cardIndex + "\r\n";
		data += card.getPin() + "\r\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		sc = new Scanner(System.in);

		boolean result = atm.InsertCard(sc, person);
		assert result;
		assert outputStream.toString().endsWith("Enter your PIN!\r\n| ");

		System.out.flush();
		System.setOut(old);

		System.out.println("***Output: " + outputStream.toString());
	}

	@Test
	void InsertCardWrongPINTest() {
		String pin = card.getPin();
		pin = pin.substring(0, 2) + "*";

		String data = "No\r\n" + cardIndex + "\r\n";
		data += pin + "\r\n" + pin + "\r\n" + pin;
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		sc = new Scanner(System.in);

		boolean result = atm.InsertCard(sc, person);
		assert !result;
		assert outputStream.toString().endsWith("Your card has been detained!\r\n");

		System.out.flush();
		System.setOut(old);

		System.out.println("***Output: " + outputStream.toString());
	}

	@Test
	void ChooseWithdraw1to6Test() {
		int value1 = 9;
		int value2 = 1;
		String data = value1 + "\r\n" + value2 + "\r\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		sc = new Scanner(System.in);
		int[] sums = {0, 10, 50, 100, 250, 500, 1000};
		int initialCredit = account.getCredit();

		boolean result = atm.WithdrawCash(sc);
		String[] lines = outputStream.toString().split("\\r\\n");
		assert result;
		assert account.getCredit() + sums[value2] == initialCredit;
		assert lines[5].endsWith("Choose a valid option!");
		assert lines[6].endsWith("Please wait while we process your transaction!");

		System.out.flush();
		System.setOut(old);

		System.out.println("***Output: " + outputStream.toString());
	}

	@Test
	void ChooseWithdraw7Test() {
		int value1 = 121;
		int value2 = 120;
		String data = "7\r\n" + value1 + "\r\n" + value2 + "\r\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		sc = new Scanner(System.in);
		int initialCredit = account.getCredit();

		boolean result = atm.WithdrawCash(sc);
		String[] lines = outputStream.toString().split("\\r\\n");
		assert result;
		assert account.getCredit() + value2 == initialCredit;
		assert lines[6].endsWith("You need to enter a sum multiple of 10!");
		assert lines[7].endsWith("Please wait while we process your transaction!");

		System.out.flush();
		System.setOut(old);

		System.out.println("***Output: " + outputStream.toString());
	}

	@Test
	void CheckBalanceTest() {

		boolean result = atm.CheckBalance();
		assert result;
		assert outputStream.toString().endsWith("Your Balance: " + account.getCredit() + account.getCurrency() + "\r\n");

		System.out.flush();
		System.setOut(old);

		System.out.println("***Output: " + outputStream.toString());
	}

	@Test
	void ChangePinTest() {
		String oldPin = card.getPin();
		String newPin1 = "11111";
		String newPin2 = "abcd";
		String newPin3 = "1234";
		String newPin4 = "9999";


		String data = oldPin + "\r\n" + newPin1 + "\r\n" + newPin2 + "\r\n" + newPin3 + "\r\n" + newPin4 + "\r\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		sc = new Scanner(System.in);

		boolean result = atm.ChangePIN(sc);
		String[] lines = outputStream.toString().split("\\r\\n");
		assert result;
		assert lines[2].endsWith("Invalid PIN! Type again! (It has to have 4 digits)");
		assert lines[3].endsWith("Invalid PIN! Type again! (It has to have 4 digits)");
		assert lines[4].endsWith("New PIN can't be the same as old PIN!");
		assert lines[5].endsWith("Your new PIN has been set!");
		assert card.getPin().equals(newPin4);

		System.out.flush();
		System.setOut(old);

		System.out.println("***Output: " + outputStream.toString());
	}

	@Test
	void DepositCashTest() {
		int value = 1000;
		int oldCredit = account.getCredit();
		String data = value + "\r\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		sc = new Scanner(System.in);

		boolean result = atm.DepositCash(sc);
		assert result;
		assert outputStream.toString().endsWith("Your credit has been added to the account!\r\n");
		assert oldCredit + value == account.getCredit();

		System.out.flush();
		System.setOut(old);

		System.out.println("***Output: " + outputStream.toString());
	}

	@Test
	void CheckForExit() {
		String data = "0\r\n0\r\n0\r\n0\r\n0\r\n";

		System.setIn(new ByteArrayInputStream(data.getBytes()));
		sc = new Scanner(System.in);

		boolean result = atm.InsertCard(sc, person);
		assert !result;
		result = atm.DepositCash(sc);
		assert !result;
		result = atm.WithdrawCash(sc);
		assert !result;
		result = atm.ChangePIN(sc);
		assert !result;
		result = atm.DepositCash(sc);
		assert !result;

		System.out.flush();
		System.setOut(old);

		System.out.println("***Output: " + outputStream.toString());

	}
}
