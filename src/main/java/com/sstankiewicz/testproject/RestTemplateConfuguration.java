package com.sstankiewicz.testproject;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Bean source for the {@code RestTemplate}
 *
 * @author sstankiewicz
 */
@Configuration
public class RestTemplateConfuguration {
    @Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
