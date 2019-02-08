package org.microboard.whiteboard.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskAccessValidator;
import org.microboard.whiteboard.model.task.visitors.TaskUploadPathGen;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.HeaderGetter;
import org.microboard.whiteboard.model.user.visitors.SidebarGetter;
import org.microboard.whiteboard.services.task.TaskService;
import org.microboard.whiteboard.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private String errorPage = "server/error";
	private String accessDeniedPage = "server/access_denied";
	private String homePage = "user/main";
	private String taskSubmissionPage = "user/task_submission";
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/tasks")
	public String getOutstandingTaskPage(Model model) {
		return homePage;
	}
	
	@GetMapping("/test")
	public String UploadPage() {
		return "user/uploadStatusView";
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
				return taskSubmissionPage;
			} else {
				return accessDeniedPage;
			}
		} else {
			return errorPage;
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
				return accessDeniedPage;
			}
		} else {
			return errorPage;
		}
	}
	
	@PostMapping("/testUpload/{id}")
	public String UploadPage(@PathVariable long id, Model model, @RequestParam("files") MultipartFile[] files) {
		User user = userService.getLoggedInUser();
		Optional<Task> maybeTask = taskService.getTask(id);
		Task task = maybeTask.get();
		String path = getPath(task, user);
		new File(path).mkdir();
		StringBuilder fileNames = new StringBuilder();
		for (MultipartFile file : files) {
			task.addFile(path + file.getOriginalFilename());
			Path fileNameAndPath = Paths.get(path,file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		taskService.updateTask(task);
		model.addAttribute("msg","Success: "+fileNames.toString());
		return "/";
	}
	
	private String getPath(Task task, User user) {
		TaskUploadPathGen pathGen = new TaskUploadPathGen();
		task.accept(pathGen);
		return pathGen.getResult();
	}
	
	@ModelAttribute("user")
	public User getUnitDirector() {
	   return userService.getLoggedInUser();
	}
	
	@ModelAttribute("header")
	public String getHeader() {
		User user = userService.getLoggedInUser();
		HeaderGetter headerGetter = new HeaderGetter();
		user.accept(headerGetter);
		return headerGetter.getResult();
	}
	
	@ModelAttribute("sidebar")
	public String getSidebar() {
		User user = userService.getLoggedInUser();
		SidebarGetter sidebarGetter = new SidebarGetter();
		user.accept(sidebarGetter);
		return sidebarGetter.getResult();
	}
}

