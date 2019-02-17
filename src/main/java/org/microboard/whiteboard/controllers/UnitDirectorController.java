package org.microboard.whiteboard.controllers;

import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.services.project.GroupProjectService;
import org.microboard.whiteboard.services.project.SoloProjectService;
import org.microboard.whiteboard.services.user.UnitDirectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unit_director")
public class UnitDirectorController {
	private Logger logger = LoggerFactory.getLogger(UnitDirectorController.class);
	
	@Autowired
	private UnitDirectorService unitDirectorService;
	
	@Autowired
	private SoloProjectService soloProjectService;
	
	@Autowired
	private GroupProjectService groupProjectService;
	
	
	@GetMapping("/projects")
	public String viewProjectsPage(Model model) {
		UnitDirector unitDirector = unitDirectorService.getLoggedInUser();
		model.addAttribute("soloProjects", soloProjectService.getByCreator(unitDirector));
		model.addAttribute("groupProjects", groupProjectService.getByCreator(unitDirector));
		return "unit_director/view_projects";
	}
	
	@GetMapping("/manage_permissions")
	public String managePermissionsPage() {
		return "manage_permissions";
	}
	
	
	@ModelAttribute("user")
	public UnitDirector getUnitDirector() {
	   return unitDirectorService.getLoggedInUser();
	}
	
}
