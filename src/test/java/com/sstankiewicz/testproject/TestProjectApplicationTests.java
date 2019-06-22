package com.sstankiewicz.testproject;

import com.sstankiewicz.testproject.controller.ProjectsController;
import com.sstankiewicz.testproject.service.RestConsumer;
import com.sstankiewicz.testproject.service.ProjectsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProjectApplicationTests {

	@Autowired private ProjectsController controller;

	@Autowired private ProjectsService projectsService;

	@Autowired private RestConsumer restConsumer;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(projectsService).isNotNull();
		assertThat(restConsumer).isNotNull();
	}
}
