package workshop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.relational.core.mapping.event.Identifier;

import java.util.UUID;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	SpeakerRepository speakers;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	BeforeConvertCallback<UuidEntity> uuidGenerator() {
		return (aggregate, id) -> {
			aggregate.id = UUID.randomUUID();
			return aggregate;
		};
	}
}
