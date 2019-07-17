package workshop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	SpeakerRepository speakers;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Override
	public void run(String... args) {

		if (speakers != null) {
			System.out.println("I'm running and have a speakers repository");
		} else {
			System.out.println("drats, something is wrong");
		}

		Speaker martin = new Speaker();
		martin.name = "Martin Fowler";

		Speaker saved = speakers.save(martin);

		System.out.println(saved);

	}
}
