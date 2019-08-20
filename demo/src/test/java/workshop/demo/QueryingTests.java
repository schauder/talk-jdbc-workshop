package workshop.demo;

import org.junit.Before;
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
public class QueryingTests {

	@Autowired
	SpeakerRepository speakers;

	@Autowired
	TalkRepository talks;

	@Autowired
	ConferenceRepository conferences;
	private Speaker tim;
	private Speaker steven;
	private Talk teams;
	private Talk mentoring;
	private Talk unicorns;
	private Conference javaLand;
	private Conference javaForum;

	@Before
	public void setup() {

		steven = speaker("Steven Schwenke");
		tim = speaker("Tim Bourguignon");
		speakers.saveAll(asList(steven, tim));

		teams = talk("Managing Distributed Teams", steven);
		mentoring = talk("Mentoring Speed Dating", steven, tim);
		unicorns = talk("Why Unicorn Developers don't Grow on Trees?", tim);
		talks.saveAll(asList(teams, mentoring, unicorns));

		javaLand = conference("JavaLand", "2020-03-17", teams, mentoring, unicorns);
		javaForum = conference("Java Forum Nord", "2019-09-24", teams, mentoring);
		conferences.saveAll(asList(javaLand, javaForum));
	}

	@Test
	public void test() {

		int count = talks.countInstancesWith(tim.id);

		assertThat(count).isEqualTo(3);
	}


	private Talk talk(String title, Speaker... speakers) {

		Talk mentoring = new Talk(title);
		mentoring.addSpeakers(speakers);
		return mentoring;
	}

	private Conference conference(String javaLand, String s, Talk... conferenceTalks) {

		Conference conference = new Conference(javaLand, LocalDate.parse(s, DateTimeFormatter.ISO_DATE));
		conference.addTalks(conferenceTalks);
		return conference;
	}

	private Speaker speaker(String name) {

		Speaker speaker = new Speaker();
		speaker.name = name;
		return speaker;
	}
}
