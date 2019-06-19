package com.sstankiewicz.testproject.controller;

import com.sstankiewicz.testproject.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

	@Autowired
	private ProjectsService projectsService;

	@GetMapping("/{user}")
	public Object getProjects(@PathVariable("user") String user) {
		//TODO
		return null;
	}
}
