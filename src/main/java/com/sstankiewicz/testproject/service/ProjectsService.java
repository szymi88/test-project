package com.sstankiewicz.testproject.service;

import java.util.Optional;

public interface ProjectsService {

	/**
	 * Returns a number of projects for the user
	 *
	 * @param user id
	 * @return the number of users projects or an empty {@code Optional}
	 * if the user was not found.
	 *
	 */
	Optional<Integer> getProjectsCount(String user);
}
