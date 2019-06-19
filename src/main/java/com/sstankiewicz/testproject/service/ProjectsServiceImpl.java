package com.sstankiewicz.testproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectsServiceImpl implements ProjectsService {

	private final GithubApiProxy githubApiProxy;

	@Autowired
	public ProjectsServiceImpl(final GithubApiProxy githubApiProxy) {
		this.githubApiProxy = githubApiProxy;
	}

	public Optional<Integer> getProjectsCount(String user) {
		return githubApiProxy.getUserRepositories(user).map(List::size);
	}
}
