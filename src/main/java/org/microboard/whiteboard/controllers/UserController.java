package org.microboard.whiteboard.controllers;

import java.util.Optional;

import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskAccessValidator;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.HomePageGetter;
import org.microboard.whiteboard.services.TaskService;
import org.microboard.whiteboard.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	
	@GetMapping("/tasks")
	public String getOutstandingTaskPage(Model model) {
		Optional<User> maybeLoggedInUser = userService.getLoggedInUser();
		if (maybeLoggedInUser.isPresent()) {
			User user = maybeLoggedInUser.get();
			HomePageGetter homePageGetter = new HomePageGetter();
			user.accept(homePageGetter);
			return homePageGetter.getResult();
		} else {
			return "redirect:login";
		}
	}
	
	@GetMapping("/tasks/{id}")
	public String getSubmissionPage(Model model, @PathVariable long id) {
		Optional<User> maybeUser = userService.getLoggedInUser();
		Optional<Task> maybeTask = taskService.getTask(id);
		if (maybeUser.isPresent()) {
			User user = maybeUser.get();
			if (maybeTask.isPresent()) {
				Task task = maybeTask.get();
				TaskAccessValidator accessValidator = new TaskAccessValidator(user);
				task.accept(accessValidator);
				if (accessValidator.getResult()) {
					model.addAttribute("task", task);
					return "submission";
				} else {
					return "access_denied";
				}
			} else {
				return "error";
			}
		} else {
			return "redirect:login";
		}
	}
	
	@PostMapping("/tasks/{id}")
	public String submitTask(@PathVariable long id, @ModelAttribute(name = "comments") String comments) {
		Optional<User> maybeUser = userService.getLoggedInUser();
		Optional<Task> maybeTask = taskService.getTask(id);
		if (maybeTask.isPresent() && maybeUser.isPresent()) {
			Task task = maybeTask.get();
			User user = maybeUser.get();
			TaskAccessValidator accessValidator = new TaskAccessValidator(user);
			task.accept(accessValidator);
			if (accessValidator.getResult()) {
				task.setTxtSubmission(comments);
				taskService.updateTask(task);
				/*
				 * Deal with file uploading here.
				 */
				return "redirect:/user/tasks/" + id;
			} else {
				return "access_denied";
			}
		} else {
			return "error";
		}
	}
	
	@ModelAttribute("user")
	public User getUnitDirector() {
	   return userService.getLoggedInUser().get();
	}
	
	
}

