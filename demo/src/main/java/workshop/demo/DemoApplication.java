package workshop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.event.AfterSaveCallback;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

import java.util.UUID;

import static java.util.Arrays.*;

@SpringBootApplication
public class DemoApplication extends AbstractJdbcConfiguration {

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

//	@Bean
//	NamingStrategy namingStrategy() {
//		return new NamingStrategy() {
//			@Override
//			public String getTableName(Class<?> type) {
//				return "T_" + NamingStrategy.super.getTableName(type);
//			}
//		};
//	}


	@Bean
	@Override
	public JdbcCustomConversions jdbcCustomConversions() {
		return new JdbcCustomConversions(asList(EmailToStringConverter.INSTANCE, StringToEmailConverter.INSTANCE));
	}

}


@WritingConverter
enum EmailToStringConverter implements Converter<Email, String> {

	INSTANCE;

	@Override
	public String convert(Email source) {
		if (source == null) {
			return null;
		}

		{
			return source.getLocalPart() + "@" + source.getDomain();
		}
	}
}

@ReadingConverter
enum StringToEmailConverter implements Converter<String, Email> {

	INSTANCE;

	@Override
	public Email convert(String source) {
		if (source == null) {
			return null;
		}

		String[] split = source.split("@");

		if (split.length != 2) {
			throw new IllegalArgumentException("Can't parse %s into a Email. Expecting exactly one @");
		}

		return new Email(split[0], split[1]);
	}
}