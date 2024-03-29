package com.sstankiewicz.testproject.controller;

import com.sstankiewicz.testproject.service.ProjectsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * REST API controller
 *
 * @author sstankiewicz
 */
@RestController
@RequestMapping("/projects")
public class ProjectsController {

    private ProjectsService projectsService;

    private final Logger logger = LoggerFactory.getLogger(ProjectsController.class);

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
            logger.error(String.format("Request for the user: %s failed.", user), e);
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

        ProjectsResponse(String userName, int projects) {
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
