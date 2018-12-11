package org.microboard.whiteboard.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.security.CustomUserDetails;
import org.microboard.whiteboard.services.UserService;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.assessment.GroupAssessment;
import org.microboard.whiteboard.model.task.GroupTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

	@Autowired
	private UserService userService;


	@PostMapping("/AddUser")
	public void addUser(@RequestBody User newUser) {
		userService.addUser(newUser);
	}

	@PutMapping("/UpdateUser/{id}")
	public void updateUser(@RequestBody User newUser, @PathVariable Long id) {//change String name to String id so that names can be found using just id
		userService.updateUser(newUser);
	}

	//TODO:Make so only unit director can make this call.
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/DeleteAllUsers")
	public void deleteAllUsers() {
		userService.DeleteAll();
	}
	@DeleteMapping("/DeleteUser/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}

	@GetMapping("/GetUser/{id}")
	public User getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}


	@GetMapping("/users/tasks")
	public String getUserTasks(Model model) {
		User newUser = new Student();
		newUser.setUserName("Alex");
		model.addAttribute("user", newUser);
		return "test";
	}
	@GetMapping("/home")
	public String getMainTasks(Model model) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User newUser = userDetails.getUser();
		
		List<Task> tasks = new ArrayList<>();
		/*
		newUser.setUserName("Alex");
		
		
		SoloProject s = new SoloProject();
		GroupProject g = new GroupProject();
		for (SoloAssessment sA : s.getAssessments()) {
			for (SoloTask sT : sA.getTasks())  { tasks.add(sT); } }
		for (GroupAssessment gA : g.getAssessments()) {
			for (GroupTask gT : gA.getTasks()) { tasks.add(gT); } }
		*/
		SoloTask x = new SoloTask();
		x.setId(100);
		x.setStatus("Marks Released");
		tasks.add(x);
		GroupTask y = new GroupTask();
		y.setId(101);
		y.setStatus("Not active");
		tasks.add(y);
		
		model.addAttribute("tasks", tasks);
		model.addAttribute("user", newUser);
		return "main_student";
	}
	@GetMapping("/main_unitDirector")
	public String getDirectorTasks(Model model) {
		User newUser = new Student();
		newUser.setUserName("Alex");
		model.addAttribute("user", newUser);
		return "main_unitDirector";
	}

}

