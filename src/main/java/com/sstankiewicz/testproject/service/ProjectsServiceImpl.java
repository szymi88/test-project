package com.sstankiewicz.testproject.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectsServiceImpl implements ProjectsService{

	public Optional<Integer> getProjectsCount(String user) {
		//TODO
		return Optional.empty();
	}
}
