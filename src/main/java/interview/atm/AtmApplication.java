package interview.atm;

import interview.model.Atm;
import interview.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
public class AtmApplication {

	public static void main(String[] args){
		SpringApplication.run(AtmApplication.class, args);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Atm a = Atm.getInstance();
		Person p = new Person("Radu");
		p.useAtm(a);
		}

}
