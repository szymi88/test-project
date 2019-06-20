package com.sstankiewicz.testproject.controller;

import com.sstankiewicz.testproject.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

	private ProjectsService projectsService;

	@Autowired
	public ProjectsController(ProjectsService projectsService) {
		this.projectsService = projectsService;
	}

	@GetMapping("/{user}")
	public ProjectsResponse getProjects(@PathVariable("user") String user) {
		Optional<Integer> projectsCount;
		try {
			projectsCount = projectsService.getProjectsCount(user);
		} catch (RuntimeException e) {
			//logger.error("Request: " + req.getRequestURL() + " raised " + ex);
			throw new ProjectsServiceException(e);
		}


		if (projectsCount.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		return new ProjectsResponse(user, projectsCount.get());
	}

	private class ProjectsResponse {
		private String user;
		private int projects;

		public ProjectsResponse(String userName, int projects) {
			this.user = userName;
			this.projects = projects;
		}

		public String getUser() {
			return user;
		}

		public int getProjects() {
			return projects;
		}
	}
}
