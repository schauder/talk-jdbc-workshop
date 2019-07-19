package workshop.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJdbcTest
@Transactional
public class AggregateReferenceTests {

	@Autowired
	SpeakerRepository speakers;

	@Autowired
	TalkRepository talks;

	@Autowired
	ConferenceRepository conferences;

	@Test
	public void speakerAndTalks() {

		Speaker steven = speaker("Steven Schwenke");
		Speaker tim = speaker("Tim Bourguignon");

		speakers.saveAll(asList(steven, tim));

		Talk teams = new Talk("Managing Distributed Teams");
		teams.addSpeakers(steven);

		Talk mentoring = new Talk("Mentoring Speed Dating");
		mentoring.addSpeakers(steven, tim);

		Talk unicorns = new Talk("Why Unicorn Developers don't Grow on Trees?");
		unicorns.addSpeakers(tim);

		talks.saveAll(asList(teams, mentoring, unicorns));

		assertThat(speakers.count()).isEqualTo(2);
		assertThat(talks.count()).isEqualTo(3);

		talks.delete(teams);

		assertThat(speakers.count()).isEqualTo(2);
		assertThat(talks.count()).isEqualTo(2);

		assertThat(talks.findById(mentoring.id).get().speakerIds).containsExactlyInAnyOrder(tim.id, steven.id);
	}

	@Test
	public void talksAndConferences() {

		Talk teams = new Talk("Managing Distributed Teams");
		Talk mentoring = new Talk("Mentoring Speed Dating");
		Talk unicorns = new Talk("Why Unicorn Developers don't Grow on Trees?");

		talks.saveAll(asList(teams, mentoring, unicorns));

		Conference javaLand = new Conference("JavaLand", LocalDate.parse("2020-03-17", DateTimeFormatter.ISO_DATE));
		javaLand.addTalks(teams, mentoring, unicorns);

		Conference javaForum = new Conference("Java Forum Nord", LocalDate.parse("2019-09-24", DateTimeFormatter.ISO_DATE));
		javaForum.addTalks(teams, mentoring);

		conferences.saveAll(asList(javaLand, javaForum));
	}

	private Speaker speaker(String name) {

		Speaker speaker = new Speaker();
		speaker.name = name;
		return speaker;
	}
}
