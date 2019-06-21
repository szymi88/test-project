package com.sstankiewicz.testproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class GithubApiProxyImpl implements GithubApiProxy {

	private final String apiUrlPattern;
	private final RestTemplate restTemplate;

	@Autowired
	public GithubApiProxyImpl(final RestTemplate restTemplate,
			final @Value("${git.repos.api.pattern}") String apiUrlPattern) {
		this.restTemplate = restTemplate;
		this.apiUrlPattern = apiUrlPattern;
	}

	public Optional<List> getUserRepositories(String user) throws ConfigurationException {
		URI url;
		try {
			url = new URL(String.format(apiUrlPattern, user)).toURI();
		} catch (MalformedURLException | URISyntaxException e) {
			throw new ConfigurationException();
		}

		try {
			return Optional.ofNullable(restTemplate.getForObject(url, List.class));
		} catch (HttpClientErrorException e) {
			if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
				return Optional.empty();
			} else {
				throw new ConfigurationException();
			}
		}
	}



}
