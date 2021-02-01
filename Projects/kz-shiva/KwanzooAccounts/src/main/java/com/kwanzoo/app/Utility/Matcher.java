package com.kwanzoo.app.Utility;

import java.util.Map;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

@Component
public class Matcher {
	public ExampleMatcher getAccountMatcher(Map<String, String> filter, boolean flag) {
		return accountRules(matcher(filter, flag));
	}

	private ExampleMatcher matcher(Map<String, String> filter, boolean flag) {
		return flag ? ExampleMatcher.matchingAny() : ExampleMatcher.matchingAll();
	}

	private ExampleMatcher accountRules(ExampleMatcher obj) {
		return obj.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ip_domain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("salesforce_id", ExampleMatcher.GenericPropertyMatchers.contains());
	}

}
