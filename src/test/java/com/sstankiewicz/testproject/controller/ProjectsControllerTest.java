package com.sstankiewicz.testproject.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sstankiewicz.testproject.service.ProjectsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectsControllerTest {

	@LocalServerPort private int port;

	@Autowired private TestRestTemplate restTemplate;

	@MockBean private ProjectsService projectsServiceMock;

	private String endpointUrl;

	@Before
	public void setUp() {
		endpointUrl = "http://localhost:" + port + "/projects/";

		Mockito.when(projectsServiceMock.getProjectsCount("valid_user")).thenReturn(Optional.of(1));
		Mockito.when(projectsServiceMock.getProjectsCount("invalid_user")).thenReturn(Optional.empty());
	}

	@Test
	public void testNonexistentUser() {
		String userName = "invalid_user";
		assertThat(getResponse(HttpMethod.GET, endpointUrl + userName).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testExistentUser() throws IOException {
		String userName = "valid_user";
		ResponseEntity<String> response = getResponse(HttpMethod.GET, endpointUrl + userName);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode responseJson = mapper.readTree(response.getBody());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		assertThat(responseJson.path("user").asText()).isEqualTo(userName);
		assertThat(responseJson.path("projects").asInt()).isEqualTo(1);
		assertThat(response.getHeaders().getContentType().getType())
				.isEqualTo(APPLICATION_JSON.getType());
	}

	@Test
	public void testEmptyUser() {
		assertThat(getResponse(HttpMethod.GET, endpointUrl).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testUnsupportedMethods() {
		String userName = "valid_user";

		assertThat(getResponse(HttpMethod.POST, endpointUrl + userName).getStatusCode())
				.isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
		assertThat(getResponse(HttpMethod.PUT, endpointUrl + userName).getStatusCode())
				.isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
		assertThat(getResponse(HttpMethod.DELETE, endpointUrl + userName).getStatusCode())
				.isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
		assertThat(getResponse(HttpMethod.TRACE, endpointUrl + userName).getStatusCode())
				.isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
	}

	private ResponseEntity<String> getResponse(HttpMethod httpMethod, String url) {
		return restTemplate.exchange(url, httpMethod, null, String.class);
	}
}