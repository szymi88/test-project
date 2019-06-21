package com.sstankiewicz.testproject.service;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GithubApiProxyImplTest {

	private GithubApiProxy githubApiProxy;

	@Mock
	private RestTemplate restTemplate;

	@Before
	public void setUp() {
		githubApiProxy = new GithubApiProxyImpl(restTemplate, "http://test.url/users/%s/repos");
	}

	@Test
	public void existingUserName() throws URISyntaxException {
		List<String> result = Arrays.asList("1", "2");
		Mockito.when(restTemplate.getForObject(new URI("http://test.url/users/valid-user/repos"), List.class))
				.thenReturn(result);

		Assertions.assertThat(githubApiProxy.getUserRepositories("valid-user")).hasValue(result);
	}

	@Test
	public void nonExistingUser() throws URISyntaxException {

		Mockito.when(restTemplate.getForObject(new URI("http://test.url/users/invalid-user/repos"), List.class))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		Assertions.assertThat(githubApiProxy.getUserRepositories("invalid-user")).isNotPresent();
	}

	@Test
	public void wrongUrl() {
		Assertions
				.assertThatThrownBy(() -> new GithubApiProxyImpl(restTemplate, "wrongurl").getUserRepositories("invalid-user"))
				.isInstanceOf(ConfigurationException.class);
	}

}