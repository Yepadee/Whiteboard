package org.microboard.whiteboard.controllers;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@Autowired
	private ProjectService projectService;

	@GetMapping("/")
	private String getMainPage() {
		return "redirect:user/tasks";
	}
	
	@GetMapping("/admin-reload")
	public String reloadElementDiv(Model model) {       
	    Project project = projectService.getProject(1);


	    model.addAttribute("project", project);

	    return "unit_director/adminForm";
	}
	
}
