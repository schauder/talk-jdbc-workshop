package workshop.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJdbcTest

@Transactional
public class UuidExternalyControlledIdRepositoryTests {

	@Autowired
	UuidEntityExternalyControlledIdRepository entities;

	@Test
	public void hardCodedInsert() {

		UuidEntityExternalyControlledId entity = new UuidEntityExternalyControlledId("Alfred");

		entities.insert(entity);

		entity.name = "Neumann";

		entities.save(entity);

		UuidEntityExternalyControlledId reloaded = entities.findById(entity.id).get();

		assertThat(reloaded.name).isEqualTo(entity.name);

	}
}
