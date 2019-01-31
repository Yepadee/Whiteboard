package org.microboard.whiteboard.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.services.project.ProjectService;
import org.microboard.whiteboard.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;

	@GetMapping("/projects")
	public List<Project> getAllMyProjects() {
		return projectService.getAllProjects();
	}
	
	@GetMapping("/projects/{id}")
	public Project getProject(@PathVariable long id) {
		Optional<Project> maybeProject = projectService.getProject(id);
		if (maybeProject.isPresent()) {
			return maybeProject.get();
		} else {
			return null;
		}
	}
	
	@PostMapping("/projects")
	public void addProject(@RequestBody Project newProject) {
		projectService.addProject(newProject);
	}
	
	@PostMapping("/projects/{id}")
	public void deleteProject(@RequestBody long id) {
		projectService.deleteProject(id);
	}
}
