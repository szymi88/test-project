package com.sstankiewicz.testproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectsServiceImpl implements ProjectsService {

	private final RestConsumer restConsumer;

	@Autowired
	ProjectsServiceImpl(final RestConsumer restConsumer) {
		this.restConsumer = restConsumer;
	}

	public Optional<Integer> getProjectsCount(String user) {
		return restConsumer.getUserRepositories(user).map(List::size);
	}
}