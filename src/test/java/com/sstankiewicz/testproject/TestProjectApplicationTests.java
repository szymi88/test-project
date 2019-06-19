package com.sstankiewicz.testproject;

import com.sstankiewicz.testproject.controller.ProjectsController;
import com.sstankiewicz.testproject.service.GithubApiProxy;
import com.sstankiewicz.testproject.service.ProjectsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProjectApplicationTests {

	@Autowired private ProjectsController controller;

	@Autowired private ProjectsService projectsService;

	@Autowired private RestTemplate restTemplate;

	@Autowired private GithubApiProxy githubApiProxy;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(projectsService).isNotNull();
		assertThat(restTemplate).isNotNull();
		assertThat(githubApiProxy).isNotNull();
	}
}
