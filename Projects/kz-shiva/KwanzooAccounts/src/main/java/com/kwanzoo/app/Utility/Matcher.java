package com.kwanzoo.app.Utility;

import java.util.Map;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

@Component
public class Matcher {
	public ExampleMatcher getMatcher(Map<String, String> filter, boolean flag) {
		return rules(matcher(filter, flag));
	}

	private ExampleMatcher matcher(Map<String, String> filter, boolean flag) {
		return flag ? ExampleMatcher.matchingAny() : ExampleMatcher.matchingAll();
	}

	private ExampleMatcher rules(ExampleMatcher obj) {
		return obj.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains());
	}

}
