package com.sstankiewicz.testproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * This class is responsible for the business logic related to the users repositories.
 *
 * @author sstankiewicz
 */
@Service
public class ProjectsServiceImpl implements ProjectsService {

	private final RestConsumer restConsumer;

	@Autowired
	ProjectsServiceImpl(final RestConsumer restConsumer) {
		this.restConsumer = restConsumer;
	}


	/**
	 * Returns a number of projects for the user
	 *
	 * @param user id
	 * @return the number of users projects or an empty {@code Optional}
	 * if the user was not found.
	 *
	 */
	public Optional<Integer> getProjectsCount(String user) {
		return restConsumer.getUserRepositories(user).map(List::size);
	}
}