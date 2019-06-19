package com.sstankiewicz.testproject.service;

import java.util.Optional;

public interface ProjectsService {
	Optional<Integer> getProjectsCount(String user);
}
