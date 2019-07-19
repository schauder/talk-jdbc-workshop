/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package workshop.demo;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jens Schauder
 */
@ToString
@EqualsAndHashCode
class Conference {

	@Id
	Long id;
	String name;
	LocalDate startDate;

	Set<TalkReference> talks = new HashSet<>();

	Conference(String name, LocalDate startDate) {
		this.name = name;
		this.startDate = startDate;
	}

	void addTalks(Talk... talks) {

		for (Talk talk : talks) {
			this.addTalk(talk);
		}
	}

	private void addTalk(Talk talk) {

		assert talk != null;
		assert talk.id != null;

		talks.add(new TalkReference(talk.id));
	}
}
