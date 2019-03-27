package org.microboard.whiteboard.controllers;

import java.io.IOException;
import java.util.List;
import org.microboard.whiteboard.dto.task.FileDto;
import org.microboard.whiteboard.model.log.TaskAction;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskAccessValidator;
import org.microboard.whiteboard.model.task.visitors.TaskFeedbackAccessValidator;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.HeaderGetter;
import org.microboard.whiteboard.model.user.visitors.OutstandingTaskGetter;
import org.microboard.whiteboard.model.user.visitors.SidebarGetter;
import org.microboard.whiteboard.services.task.TaskService;
import org.microboard.whiteboard.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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
	private String accessDeniedPage = "server/access_denied";
	private String taskSubmissionPage = "user/task_submission";
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/tasks")
	public String getOutstandingTaskPage(Model model) {
		OutstandingTaskGetter otg = new OutstandingTaskGetter();
		User user = userService.getLoggedInUser();
		user.accept(otg);
		return otg.getResult();
	}
	
	@GetMapping("/tasks/{id}")
	public String getSubmissionPage(Model model, @PathVariable long id) {
		User user = userService.getLoggedInUser();
		Task task = taskService.getTask(id);

		TaskAccessValidator accessValidator = new TaskAccessValidator(user);
		task.accept(accessValidator);
		if (accessValidator.getResult()) {
			List<FileDto> fileinfo = task.getFileInfo();
			model.addAttribute("fileinfo", fileinfo);
			model.addAttribute("task", task);
			return taskSubmissionPage;
		} else {
			return accessDeniedPage;
		}
	}
	
	@GetMapping("/tasks/download/{id}/{filename}")
	public ResponseEntity<Resource> downloadFile(Model model, @PathVariable long id,  @PathVariable String filename) {
		User user = userService.getLoggedInUser();
		Task task = taskService.getTask(id);

		TaskFeedbackAccessValidator accessValidator = new TaskFeedbackAccessValidator(user);
		task.accept(accessValidator);
		if (accessValidator.getResult()) {
			return taskService.downloadFile(id, filename);
		}
		return null;
	}
	
	@PostMapping(value="/tasks/delete/{id}" , params={"deleteFile"})
	public String getDeletePage(@PathVariable Long id,  @RequestParam("deleteFile") String filename) {
		//TODO make dedicated query for task validation
		User user = userService.getLoggedInUser();
//		System.out.println("if");

		Task task = taskService.getTask(id);
		TaskAccessValidator accessValidator = new TaskAccessValidator(user);
		task.accept(accessValidator);
		if (accessValidator.getResult()) {
//			System.out.println("if");
			taskService.deleteFile(id, filename);
			return "redirect:/user/tasks/" + id;
		} else {
//			System.out.println("Acess denied");
			return accessDeniedPage;
		}
	}
	
	@PostMapping("/tasks/{id}")
	public String submitTask(@PathVariable long id,
		@ModelAttribute(name = "comments") String comments,
		@RequestParam("files") MultipartFile[] files) throws IOException {
		Task task = taskService.getTask(id);
		User user = userService.getLoggedInUser();
		TaskAccessValidator accessValidator = new TaskAccessValidator(user);
		task.accept(accessValidator);
		task.addAction(new TaskAction(user, "Changed comment to "  + "\"" + comments + "\""));
		if (accessValidator.getResult()) {
			taskService.submitFiles(id, files, comments);
			return "redirect:/user/tasks/" + id;
		} else {
			return accessDeniedPage;
		}
	}
	
	@GetMapping("/log")
	public String getLogPage(Model model) {
		return "student/log";
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

