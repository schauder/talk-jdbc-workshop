package workshop.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJdbcTest
public class SpeakerRepositoryTests {

	@Autowired
	SpeakerRepository speakers;

	@Test
	public void saveInstance() {

		Speaker martin = new Speaker();
		martin.name = "Martin Fowler";

		Speaker saved = speakers.save(martin);

		assertThat(saved.id).isNotNull();
	}


	@Test
	public void copyInstance() {

		Speaker martin = new Speaker();
		martin.name = "Martin Fowler";
		Speaker saved = speakers.save(martin);
		assertThat(saved.id).isNotNull();


		saved.id = null;

		Speaker savedAgain = speakers.save(martin);

		assertThat(savedAgain.id).isNotNull();

		assertThat(speakers.findAll()).hasSize(2);
	}

	@Test
	public void reloadInstance() {

		Speaker martin = new Speaker();
		martin.name = "Martin Fowler";
		Speaker saved = speakers.save(martin);
		assertThat(saved.id).isNotNull();

		Optional<Speaker> loaded = speakers.findById(saved.id);
		Optional<Speaker> reloaded = speakers.findById(saved.id);

		assertThat(loaded.get())
				.isEqualTo(reloaded.get())
				.isNotSameAs(reloaded.get());

	}

}
