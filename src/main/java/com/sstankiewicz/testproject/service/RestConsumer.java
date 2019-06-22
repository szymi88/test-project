package com.sstankiewicz.testproject.service;

import java.util.List;
import java.util.Optional;

public interface RestConsumer {
	Optional<List> getUserRepositories(String user);
}
