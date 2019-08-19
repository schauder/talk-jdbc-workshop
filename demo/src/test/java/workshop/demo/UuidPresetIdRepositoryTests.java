package workshop.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJdbcTest

@Transactional
public class UuidPresetIdRepositoryTests {

	@Autowired
	UuidEntityPresetIdRepository entities;

	@Test
	public void generateIdInCallback() {

		UuidEntityPresetId entity = new UuidEntityPresetId("Alfred");

		entity = entities.save(entity);

		entity.name = "Neumann";

		entities.save(entity);

		UuidEntityPresetId reloaded = entities.findById(entity.id).get();

		assertThat(reloaded.name).isEqualTo(entity.name);

		assertThat(reloaded.isNew).isFalse();
		assertThat(entity.isNew).isFalse();

	}
}
