package org.microboard.whiteboard.controllers;

import java.util.List;

import org.microboard.whiteboard.services.UserService;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/input")
	
	@PostMapping("/user")
	public void addUser(@RequestBody User newUser) {
		userService.addUser(newUser);
	}
	
	@GetMapping("/testUser")
	public void testAddUser() {
//		User u=new User();
//		u.setName("ABCD");
//		u.setPassword("12345");
		userService.addUser(new User("A","12345"));
		userService.addUser(new User("B","12345"));
		userService.addUser(new User("C","12345"));
		userService.addUser(new User("D","12345"));
		userService.addUser(new User("E","12345"));
		userService.addUser(new User("F","12345"));
	}
	
	/*
	@GetMapping("/test")
	public void addAssessment() {
		projectService.getAllProjects().get(0).getAssessments().add(new SoloAssessment());
	}
	*/
}
