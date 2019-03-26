package org.microboard.whiteboard.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.microboard.whiteboard.dto.task.FileDto;
import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.feedback.GroupMemberFeedback;
import org.microboard.whiteboard.model.task.GroupTask;
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
	public String FeedbackUploadPage(@PathVariable Long task_id, Model model) {
		Task task = taskService.getTask(task_id);
		TaskFeedbackPageGetter feedbackGetter = new TaskFeedbackPageGetter(model);
		task.accept(feedbackGetter);
		
		Assessor assessor = assessorService.getLoggedInUser();
		Feedback feedback = task.getIndividualFeedback(assessor);
		List<FileDto> fileinfo = feedback.getFileInfo();
		getFeedbackHashMap(task,assessor, model);
		model.addAttribute("feedbackfileinfo", fileinfo);
		model.addAttribute("feedback", feedback);
		model.addAttribute("taskfileinfo", task.getFileInfo());
		return feedbackGetter.getResult();
	}
	
	public void getFeedbackHashMap(Task task, Assessor assessor, Model model) {
		GroupTask groupTask = null;
		if (task instanceof GroupTask) {
			groupTask = (GroupTask) task;
			Map<Long,Optional<Feedback>> individualFeedbacks = new HashMap<>();
			Map<User, GroupMemberFeedback> groupMemberFeedback = groupTask.getGroupMemberFeedback();
		
			for (User members : groupTask.getAccountable().getMembers()) {
				Map<Assessor,Feedback> assessorFeedbacks = groupMemberFeedback.get(members).getFeedback();
				Optional<Feedback> opFeedback = Optional.empty();
				if (assessorFeedbacks.containsKey(assessor)) opFeedback = Optional.of(assessorFeedbacks.get(assessor));
					individualFeedbacks.put(members.getId(), opFeedback);
					System.out.println("Optional Feedback with user " + members.getName() + " : " + opFeedback);
				}
			model.addAttribute("individualFeedbacks", individualFeedbacks);
		}
	}
	
	@PostMapping("/feedback/{task_id}")
	public String submitFeedback(@PathVariable Long task_id,
		@RequestParam(name = "comments") String comments,
		@RequestParam(name = "marks") Integer marks,
		@RequestParam(name = "visible") Optional<?> visible,
		@RequestParam("files") MultipartFile[] files) throws IOException {
		Long feedbackId = taskService.getTask(task_id).getFeedback().get(assessorService.getLoggedInUser()).getId();
		feedbackService.submitFiles(feedbackId, files, comments, marks, visible.isPresent());
		return "redirect:/assessor/feedback/" + task_id;
		
		//--------------------------------------
		//NO ACCESS VALIDATION- TODO!
	}
	
	@PostMapping("/group_feedback/{task_id}")
	public String submitGroupFeedback(@PathVariable Long task_id,
		@RequestParam(name = "comments") String comments,
		@RequestParam(name = "marks") Integer marks,
		@RequestParam(name = "visible") Optional<?> visible,
		@RequestParam("files") MultipartFile[] files) throws IOException {
		Long feedbackId = taskService.getTask(task_id).getFeedback().get(assessorService.getLoggedInUser()).getId();
		feedbackService.submitFiles(feedbackId, files, comments, marks, visible.isPresent());
		return "redirect:/assessor/feedback/" + task_id;
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
