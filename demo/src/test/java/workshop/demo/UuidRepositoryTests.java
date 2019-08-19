package workshop.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJdbcTest

@Transactional
public class UuidRepositoryTests {

	@Autowired
	UuidEntityRepository entities;

	@Test
	public void generateIdInCallback() {

		UuidEntity entity = new UuidEntity();

		entities.save(entity);
	}
}
