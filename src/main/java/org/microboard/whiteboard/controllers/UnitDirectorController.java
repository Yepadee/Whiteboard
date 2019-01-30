package org.microboard.whiteboard.controllers;

import java.util.List;

import org.microboard.whiteboard.model.project.builder.ProjectTemplate;
import org.microboard.whiteboard.model.project.builder.SoloProjectTemplate;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.services.user.AssessorService;
import org.microboard.whiteboard.services.user.StudentService;
import org.microboard.whiteboard.services.user.UnitService;
import org.microboard.whiteboard.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unit_director")
public class UnitDirectorController {
	
	Logger logger = LoggerFactory.getLogger(UnitDirectorController.class);

	@Autowired
	private AssessorService assessorService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/new_solo_project")
	public String getNewSoloProjectPage(Model model) {
		List<Unit> allUnits = unitService.getAllUnits();
		List<Assessor> allAssessors = assessorService.getAllUsers();
		
		model.addAttribute("units", allUnits);
		model.addAttribute("assessors", allAssessors);
		
		return "new_project";
	}
	
	@PostMapping("/new_solo_project")
	public String makeNewSoloProject(Model model, @ModelAttribute SoloProjectTemplate projectTemplate) {
		logger.info("New project added.");
		
		logger.info("Added new solo project with name: " + projectTemplate.getName());
		
		return "new_project";
	}
	
	@GetMapping("/edit_project/{id}")
	public String editProjectPage() {
		return "edit_project";
	}
	
	@GetMapping("/projects")
	public String viewProjectsPage() {
		return "view_projects";
	}
	
	@GetMapping("/manage_permissions")
	public String managePermissionsPage() {
		return "manage_permissions";
	}
	
	@ModelAttribute("user")
	public UnitDirector getUnitDirector() {
	   return (UnitDirector) userService.getLoggedInUser();
	}
	
}
