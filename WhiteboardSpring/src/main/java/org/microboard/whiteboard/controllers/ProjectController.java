package org.microboard.whiteboard.controllers;

import java.util.List;

import org.microboard.whiteboard.services.ProjectService;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.SoloProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;

	@GetMapping("/projects")
	public List<Project> getAllMyProjects() {
		return projectService.getAllProjects();
	}
	
	@PostMapping("/projects")
	public void addProject(@RequestBody Project newProject) {
		projectService.addProject(newProject);
	}
	
	@GetMapping("/test")
	public void testAddProject() {
		projectService.addProject(new SoloProject());
	}
	/*
	@GetMapping("/test")
	public void addAssessment() {
		projectService.getAllProjects().get(0).getAssessments().add(new SoloAssessment());
	}
	*/
}
