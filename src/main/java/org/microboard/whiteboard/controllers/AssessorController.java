package org.microboard.whiteboard.controllers;

import java.io.IOException;
import java.util.List;

import org.microboard.whiteboard.dto.task.FileDto;
import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskFeedbackPageGetter;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.HeaderGetter;
import org.microboard.whiteboard.model.user.visitors.SidebarGetter;
import org.microboard.whiteboard.services.task.FeedbackService;
import org.microboard.whiteboard.services.task.TaskService;
import org.microboard.whiteboard.services.user.AssessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/assessor")
public class AssessorController {
	
	@Autowired
	private AssessorService assessorService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@GetMapping("/feedback/{task_id}")
	public String FeedbackUploadPage(@PathVariable long task_id, Model model) {
		Task task = taskService.getTask(task_id);
		TaskFeedbackPageGetter feedbackGetter = new TaskFeedbackPageGetter(model);
		task.accept(feedbackGetter);
		
		Assessor assessor = assessorService.getLoggedInUser();
		Feedback feedback = task.getIndividualFeedback(assessor);
		List<FileDto> fileinfo = feedbackService.createFileInfoInstance(feedback);
		model.addAttribute("feedbackfileinfo", fileinfo);
		model.addAttribute("task", task);
		model.addAttribute("feedback", feedback);
		model.addAttribute("taskfileinfo", taskService.createFileInfoInstance(task));
		return feedbackGetter.getResult();
	}
	
	@PostMapping("/feedback/{id}")
	public String submitFeedback(@PathVariable long id,
		@ModelAttribute(name = "comments") String comments,
		@ModelAttribute(name = "marks") Integer marks,
		@RequestParam("files") MultipartFile[] files) throws IOException {
		feedbackService.submitFiles(id, files, comments, marks);
		return "redirect:/assessor/feedback/" + id;
		
		//--------------------------------------
		//NO ACCESS VALIDATION- TODO!
	}
	
	@PostMapping("/group_feedback/{id}")
	public String submitGroupFeedback(@PathVariable long id,
		@ModelAttribute(name = "comments") String comments,
		@ModelAttribute(name = "marks") Integer marks,
		@RequestParam("files") MultipartFile[] files) throws IOException {
		feedbackService.submitFiles(id, files, comments, marks);
		return "redirect:/assessor/feedback/" + id;
		
		//--------------------------------------
		//NO ACCESS VALIDATION- TODO!
	}
	
	@GetMapping("/feedback/download/{id}/{filename}")
	public ResponseEntity<Resource> downloadFile(Model model, @PathVariable long id,  @PathVariable String filename) {
		Assessor assessor = assessorService.getLoggedInUser();
		Task task = taskService.getTask(id);
		Feedback feedback = task.getIndividualFeedback(assessor);
		return feedbackService.downloadFile(feedback, filename);
	}
	
	@PostMapping(value = "/feedback/delete/{id}" , params={"deleteFeedback"})
	public String getDeletePage(@PathVariable Long id,  @RequestParam("deleteFeedback") String filename) {
		Assessor assessor = assessorService.getLoggedInUser();
		Task task = taskService.getTask(id);
		Feedback feedback = task.getIndividualFeedback(assessor);
		
		feedbackService.deleteFile(feedback, filename);
		return "redirect:/assessor/feedback/" + id;
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
