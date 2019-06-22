package com.sstankiewicz.testproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Bean source for the {@code RestTemplate}
 *
 * @author sstankiewicz
 */
@Configuration
public class RestTemplateConfuguration {

	private String acceptHeader;

	public RestTemplateConfuguration(@Value("${accept.header}") final String acceptHeader) {
		this.acceptHeader = acceptHeader;
	}


	/**
	 * Provides configured {@code RestTemplate}
	 *
	 * Sets interceptor to add AcceptHeader if the {accept.header} property is set.
	 *
	 * @param builder
	 * @return configured RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		RestTemplate build = builder.build();

		if (StringUtils.isEmpty(acceptHeader)) {
			return build;
		}

		setAcceptHeader(build);
		return build;
	}

	private void setAcceptHeader(RestTemplate build) {
		build.setInterceptors(Collections.singletonList((request, body, execution) -> {
			request.getHeaders().setAccept(MediaType.parseMediaTypes(acceptHeader));
			return execution.execute(request, body);
		}));
	}
}
