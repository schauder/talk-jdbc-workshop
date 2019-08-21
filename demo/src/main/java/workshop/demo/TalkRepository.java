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

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface TalkRepository extends CrudRepository<Talk, Long> {

	@Query("select count(*) " +
			"from Conference_Talk ct " +
			"join Talk t " +
			"on ct.talk = t.id " +
			"where :speakerId in (unnest(t.speaker_ids))")
	int countInstancesWith(Long speakerId);

	@Modifying
	@Query("update talk t " +
			"set title = " +
			"title || ' (with ' || (select s.name from speaker s where s.id = t.speaker_ids[1])  || ')' ")
	void addSpeakerToTitle();

	@Query(value = "select t.title, c.name " +
			"from talk t " +
			"join conference_talk ct on t.id = ct.talk " +
			"join conference c on c.id = ct.conference " +
			"order by t.title"
			, resultSetExtractorClass = TalkInfoExtractor.class
	)
	List<TalkInfo> talkInfos();
}
