package org.microboard.whiteboard.controllers;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.microboard.whiteboard.dto.task.FileDto;
import org.microboard.whiteboard.dto.user.SelectedUsersDto;
import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.visitors.EditPathGetter;
import org.microboard.whiteboard.model.project.visitors.ProjectModelFiller;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.ReconciliationPageGetter;
import org.microboard.whiteboard.model.task.visitors.TaskProjectGetter;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.UserPermChangeValidator;
import org.microboard.whiteboard.services.project.ProjectService;
import org.microboard.whiteboard.services.task.FeedbackService;
import org.microboard.whiteboard.services.task.TaskService;
import org.microboard.whiteboard.services.user.AssessorService;
import org.microboard.whiteboard.services.user.StudentService;
import org.microboard.whiteboard.services.user.UnitDirectorService;
import org.microboard.whiteboard.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/unit_director")
public class UnitDirectorController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private AssessorService assessorService;
	
	@Autowired
	private UnitDirectorService unitDirectorService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FeedbackService feedbackService;
	
	
	@GetMapping("/projects")
	public String viewProjectsPage(Model model) {
		UnitDirector unitDirector = unitDirectorService.getLoggedInUser();
		model.addAttribute("projects", unitDirector.getMyProjects());
		model.addAttribute("assignedProjects", unitDirector.getAssignedProjects());
		
		return "unit_director/view_projects";
	}
	
	@GetMapping("/edit_project/{id}")
	public String editProject(@PathVariable Long id) {
		Project project = projectService.getProject(id);
		EditPathGetter epg = new EditPathGetter();
		project.accept(epg);
//		System.out.println(epg.getResult());
		return "redirect:" + epg.getResult() + "/" + id;
	}
	
	@GetMapping("/add_extensions/{id}")
	public String addExtensions(@PathVariable Long id,Model model)
	{
		Project project = projectService.getProject(id);
//		EditPathGetter epg = new EditPathGetter();
//		project.accept(epg);
//		model.addAttribute("project",project);
		ProjectModelFiller pmf = new ProjectModelFiller(model);
		project.accept(pmf);
		
		System.out.println("Entered add_extensions");
		
		return "unit_director/add_extensions";
	}
	@PostMapping("/save_extensions/student/{task_id}")
	public String editExtensionStudent(@PathVariable Long task_id,
		@RequestParam(name = "studentDeadline") Date date
		/*@RequestParam("studentDeadline") 
    	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date*/) throws IOException {
		Task task = taskService.getTask(task_id);
		//System.out.println("reached:");
		//Long feedbackId = taskService.getTask(task_id).getReconciledFeedback().getId();
		task.setStudentExtension(date);
		//System.out.println(task.getStudentExtension());
		taskService.updateTask(task);
		TaskProjectGetter tpg = new TaskProjectGetter();
		task.accept(tpg);
		return "redirect:/unit_director/add_extensions/" + tpg.getResult().getId();
		
		//--------------------------------------
		//NO ACCESS VALIDATION- TODO!
	}

	@PostMapping("/save_extensions/marker/{task_id}")
	public String editExtensionMarker(@PathVariable Long task_id,
		@RequestParam(name = "markerDeadline") Date date
		/*@RequestParam("studentDeadline") 
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date*/) throws IOException {
		Task task = taskService.getTask(task_id);
		System.out.println("reached:");
		//Long feedbackId = taskService.getTask(task_id).getReconciledFeedback().getId();
		task.setMarkerExtension(date);
		System.out.println(task.getMarkerExtension());
		taskService.updateTask(task);
		TaskProjectGetter tpg = new TaskProjectGetter();
		task.accept(tpg);
		return "redirect:/unit_director/add_extensions/" + tpg.getResult().getId();
		
		//--------------------------------------
		//NO ACCESS VALIDATION- TODO!
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");   
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, null,  new CustomDateEditor(dateFormat, true));
	}
	
	@GetMapping("/reconciliation/{id}")
	public String reconciliation(Model model, @PathVariable Long id) {
		Task task = taskService.getTask(id);
		ReconciliationPageGetter rcg = new ReconciliationPageGetter(model);
		task.accept(rcg);
		
		Feedback feedback = task.getReconciledFeedback();
		List<FileDto> fileinfo = feedback.getFileInfo();
		model.addAttribute("reconciliationFileinfo", fileinfo);
		model.addAttribute("task", task);
		model.addAttribute("reconciledFeedback", feedback);
		model.addAttribute("taskfileinfo", task.getFileInfo());
		
		return rcg.getResult();
	}
	
	@PostMapping("/reconciliation/{task_id}")
	public String submitReconciliation(@PathVariable Long task_id,
		@ModelAttribute(name = "comments") String comments,
		@ModelAttribute(name = "marks") Integer marks,
		@RequestParam("files") MultipartFile[] files) throws IOException {
		Feedback feedback = taskService.getTask(task_id).getReconciledFeedback();
		//Long feedbackId = taskService.getTask(task_id).getReconciledFeedback().getId();
		feedbackService.submitFeedback(feedback, files, comments, marks, true);
		return "redirect:/unit_director/reconciliation/" + task_id;
		
		//--------------------------------------
		//NO ACCESS VALIDATION- TODO!
	}
	
	@PostMapping(value = "/reconciliation/delete/{id}")
	public String getDeletePage(@PathVariable Long id,  @RequestParam("deleteFeedback") String filename) {
		Task task = taskService.getTask(id);
		Feedback feedback = task.getReconciledFeedback();
		feedback.setStatus("completed");
		
		feedbackService.deleteFile(feedback, filename);
		
		taskService.updateTask(task);
		return "redirect:/unit_director/reconciliation/" + id;
	}
	
	@PostMapping("/feedback/{task_id}")
	public String submitFeedback(@PathVariable long task_id,
		@ModelAttribute(name = "comments") String comments,
		@ModelAttribute(name = "marks") Integer marks,
		@RequestParam("files") MultipartFile[] files) throws IOException {
		Feedback feedback = taskService.getTask(task_id).getFeedback().get(assessorService.getLoggedInUser());
		//Long feedbackId = taskService.getTask(task_id).getFeedback().get(assessorService.getLoggedInUser()).getId();
		feedbackService.submitFeedback(feedback, files, comments, marks, true);
		return "redirect:/assessor/feedback/" + task_id;
		
		//--------------------------------------
		//NO ACCESS VALIDATION- TODO!
	}
	
	@GetMapping("/manage_perms")
	public String managePermissionsPage(Model model) {
		model.addAttribute("selectedUsersDto", new SelectedUsersDto());
		model.addAttribute("students", studentService.getAllUsers());
		model.addAttribute("assessors", assessorService.getOnlyAssessors());
		model.addAttribute("unitDirectors", unitDirectorService.getAllUsers());
		return "unit_director/manage_perms";
	}
	
	@PostMapping(value="/manage_perms")
	public String setAssessor(Model model, SelectedUsersDto selectedUsersDto) {
		String newPerms = selectedUsersDto.getNewPerms();
		UserPermChangeValidator validator = new UserPermChangeValidator(newPerms);
		boolean error = false;
		String errorMsg = "";
		for (User user : selectedUsersDto.getSelectedUsers()) {
			user.accept(validator);
			boolean valid = validator.getResult();
			if (valid) {
				if (getUnitDirector().getId() != user.getId()) {
					userService.detachUser(user);
					userService.changePerms(user.getId(), newPerms);
					User assessor = userService.getUser(user.getId());
					userService.persistUser(assessor);	
				} else {
					error = true;
					errorMsg += "You may not change your own permissions.";
				}
			} else {
				error = true;
			}
		}
		if (error) {
			errorMsg += validator.getErrorMsg();
			model.addAttribute("errorMsg", errorMsg);
		}
		
		model.addAttribute("selectedUsersDto", new SelectedUsersDto());
		model.addAttribute("students", studentService.getAllUsers());
		model.addAttribute("assessors", assessorService.getOnlyAssessors());
		model.addAttribute("unitDirectors", unitDirectorService.getAllUsers());
	    return "unit_director/manage_perms";
	}
	
	
	@ModelAttribute("user")
	public UnitDirector getUnitDirector() {
	   return unitDirectorService.getLoggedInUser();
	}
	
}
