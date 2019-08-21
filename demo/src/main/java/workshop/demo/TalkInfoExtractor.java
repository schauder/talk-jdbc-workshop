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

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jens Schauder
 */
public class TalkInfoExtractor implements ResultSetExtractor {
	@Override
	public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {

		String currentTalk = "";
		List<TalkInfo> result = new ArrayList<>();

		while (resultSet.next()) {
			String talkName = resultSet.getString(1);
			if (result.isEmpty() || !currentTalk.equals(talkName)) {
				result.add(new TalkInfo(talkName));
				currentTalk = talkName;
			}
			result.get(result.size()-1).addConference(resultSet.getString(2));
		}

		return result;
	}
}
