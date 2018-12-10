package org.microboard.whiteboard.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.services.GroupProjectService;
import org.microboard.whiteboard.services.ProjectService;
import org.microboard.whiteboard.services.SoloProjectService;
import org.microboard.whiteboard.services.UserService;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
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
	
	@GetMapping("/test")
	public void testAddProject() {
		List<User> cohort = new ArrayList<>();
		User user1 = new Student();
		user1.setUserName("James");
		
		User user2 = new Student();
		user2.setUserName("Alex");
		
		userService.addUser(user1);
		userService.addUser(user2);
		
		cohort.add(user1);
		cohort.add(user2);
		
		SoloProject project = new SoloProject();
		project.setName("Test 1");
		project.setCreator(null);
		project.setDescription("A test solo project.");
		project.setHelpers(new ArrayList<>());
		project.getCohort().addAll(cohort);
		
		SoloAssessment assessment1 = new SoloAssessment();
		assessment1.setName("solo assessment 1");
		assessment1.setDescription("A test solo assessment.");
		assessment1.setMarkerDeadline(new Date());
		assessment1.setStudentDeadline(new Date());
		
		project.addAssessment(assessment1);
		projectService.addProject(project);
		
		for (User user : project.getCohort()) {
			SoloTask task = new SoloTask();
			task.setAccountable(user);
			assessment1.addTask(task);
		}
	}
}
