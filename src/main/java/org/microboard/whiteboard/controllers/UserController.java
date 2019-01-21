package org.microboard.whiteboard.controllers;

import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskAccessValidator;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.HomePageGetter;
import org.microboard.whiteboard.services.TaskService;
import org.microboard.whiteboard.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private TaskService taskService;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	
	@GetMapping("/outstanding_tasks")
	public String getOutstandingTaskPage(Model model) {
		Optional<User> maybeLoggedInUser = userService.getLoggedInUser();
		if (maybeLoggedInUser.isPresent()) {
			User user = maybeLoggedInUser.get();
			List<Task> tasks = user.getAllTasks();
			
			model.addAttribute("tasks", tasks);
			model.addAttribute("user", user);

			//Get the correct page to visit depending on user type.
			//Pages returned can be changed in "mode/user/visitors/HomePageGetter"
			HomePageGetter homePageGetter = new HomePageGetter();
			user.accept(homePageGetter);
			return homePageGetter.getResult();
		} else {
			return "redirect:login";
		}
	}
	
	@GetMapping("/submitted_tasks")
	public String getSubmittedTaskPage(Model model) {
		Optional<User> maybeLoggedInUser = userService.getLoggedInUser();
		if (maybeLoggedInUser.isPresent()) {
			User user = maybeLoggedInUser.get();
			List<Task> tasks = user.getAllTasks();
			model.addAttribute("tasks", tasks);
			model.addAttribute("user", user);
			HomePageGetter homePageGetter = new HomePageGetter();
			user.accept(homePageGetter);
			return homePageGetter.getResult();
		} else {
			return "redirect:login";
		}
	}
	
	@GetMapping("/marked_tasks")
	public String getMarkedTaskPage(Model model) {
		Optional<User> maybeLoggedInUser = userService.getLoggedInUser();
		if (maybeLoggedInUser.isPresent()) {
			User user = maybeLoggedInUser.get();
			List<Task> tasks = user.getAllTasks();
			model.addAttribute("tasks", tasks);
			model.addAttribute("user", user);
			HomePageGetter homePageGetter = new HomePageGetter();
			user.accept(homePageGetter);
			return homePageGetter.getResult();
		} else {
			return "redirect:login";
		}
	}
	
	@GetMapping("/tasks/{id}")
	public String getSubmissionPage(Model model, @PathVariable long id) {
		System.out.println("TEST 4");
		Optional<User> maybeUser = userService.getLoggedInUser();
		System.out.println("TEST 5");
		Optional<Task> maybeTask = taskService.getTask(id);
		System.out.println("TEST 1");
		if (maybeTask.isPresent() && maybeUser.isPresent()) {
			Task task = maybeTask.get();
			User user = maybeUser.get();
			TaskAccessValidator accessValidator = new TaskAccessValidator(user);
			task.accept(accessValidator);
			System.out.println("TEST 2");
			if (accessValidator.getResult()) {
				return "submission"; //TODO: create submission page and hook up to model.
			} else {
				return "access_denied";
			}
		} else {
			return "error";
		}
	}
	
}

