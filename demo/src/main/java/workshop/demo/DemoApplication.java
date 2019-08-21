package workshop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.relational.core.mapping.event.AfterSaveCallback;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

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
		return aggregate -> {
			aggregate.id = UUID.randomUUID();
			return aggregate;
		};
	}

	@Bean
	AfterSaveCallback<UuidEntityPresetId> markAsSaved() {
		return aggregate -> {
			aggregate.isNew = false;
			return aggregate;
		};
	}
}
