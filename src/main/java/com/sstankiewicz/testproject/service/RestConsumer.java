package com.sstankiewicz.testproject.service;

import java.util.List;
import java.util.Optional;

public interface RestConsumer {

	/**
	 * Returns list of users repositories.
	 *
	 * @param user id
	 * @return the list of user's repositories or an empty {@code Optional}
	 * if the user was not found.
	 */
	Optional<List> getUserRepositories(String user);
}
