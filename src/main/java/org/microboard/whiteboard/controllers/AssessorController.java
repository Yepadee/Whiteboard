package org.microboard.whiteboard.controllers;

import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskFeedbackPageGetter;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.HeaderGetter;
import org.microboard.whiteboard.model.user.visitors.SidebarGetter;
import org.microboard.whiteboard.services.task.TaskService;
import org.microboard.whiteboard.services.user.AssessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/assessor")
public class AssessorController {
	
	@Autowired
	private AssessorService assessorService;
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping("/feedback/{task_id}")
	public String FeedbackUploadPage(@PathVariable long task_id, Model model) {
		Task task = taskService.getTask(task_id);
		TaskFeedbackPageGetter feedbackGetter = new TaskFeedbackPageGetter(model);
		task.accept(feedbackGetter);
		model.addAttribute("fileinfo", taskService.createFileInfoInstance(task));
		return feedbackGetter.getResult();
	}
	
	@ModelAttribute("user")
	public Assessor getAssessor() {
	   return assessorService.getLoggedInUser();
	}
	
	@ModelAttribute("header")
	public String getHeader() {
		User user = assessorService.getLoggedInUser();
		HeaderGetter headerGetter = new HeaderGetter();
		user.accept(headerGetter);
		return headerGetter.getResult();
	}
	
	@ModelAttribute("sidebar")
	public String getSidebar() {
		User user = assessorService.getLoggedInUser();
		SidebarGetter sidebarGetter = new SidebarGetter();
		user.accept(sidebarGetter);
		return sidebarGetter.getResult();
	}
}
