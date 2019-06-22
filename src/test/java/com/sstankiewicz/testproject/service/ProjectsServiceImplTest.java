package com.sstankiewicz.testproject.service;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProjectsServiceImplTest {

	private ProjectsService projectsService;

	@Mock
	private RestConsumer restConsumer;

	@Before
	public void setUp() {

		projectsService = new ProjectsServiceImpl(restConsumer);

		Mockito.when(restConsumer.getUserRepositories("valid-user"))
				.thenReturn(Optional.of(Collections.singletonList(new Object())));

		Mockito.when(restConsumer.getUserRepositories("invalid-user"))
				.thenReturn(Optional.empty());
	}

	@Test
	public void validUser() {
		Assertions.assertThat(projectsService.getProjectsCount("valid-user")).isEqualTo(Optional.of(1));
	}

	@Test
	public void invalidUser() {
		Assertions.assertThat(projectsService.getProjectsCount("invalid-user")).isNotPresent();
	}
}