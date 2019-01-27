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
		User user = userService.getLoggedInUser();
		HomePageGetter homePageGetter = new HomePageGetter(model);
		user.accept(homePageGetter);
		return homePageGetter.getResult();
	}
	
	@GetMapping("/tasks/{id}")
	public String getSubmissionPage(Model model, @PathVariable long id) {
		User user = userService.getLoggedInUser();
		Optional<Task> maybeTask = taskService.getTask(id);
		if (maybeTask.isPresent()) {
			Task task = maybeTask.get();
			TaskAccessValidator accessValidator = new TaskAccessValidator(user);
			task.accept(accessValidator);
			if (accessValidator.getResult()) {
				model.addAttribute("task", task);
				model.addAttribute("user", user);
				return "submission";
			} else {
				return "access_denied";
			}
		} else {
			return "error";
		}
	}
	
	@PostMapping("/tasks/{id}")
	public String submitTask(@PathVariable long id, @ModelAttribute(name = "comments") String comments) {
		Optional<Task> maybeTask = taskService.getTask(id);
		if (maybeTask.isPresent()) {
			Task task = maybeTask.get();
			User user = userService.getLoggedInUser();
			TaskAccessValidator accessValidator = new TaskAccessValidator(user);
			task.accept(accessValidator);
			if (accessValidator.getResult()) {
				task.setTxtSubmission(comments);
				taskService.updateTask(task);
				/*TODO:
				 * Record date
				 * Add action to log
				 * Build file uploader/downloader
				 */
				return "redirect:/user/tasks/" + id;
			} else {
				return "access_denied";
			}
		} else {
			return "error";
		}
	}
}

