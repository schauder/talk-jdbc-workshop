package workshop.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
