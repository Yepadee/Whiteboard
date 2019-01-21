package org.microboard.whiteboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unit_director")
public class UnitDirectorController {
	
	@GetMapping("/new_project")
	public String getNewProjectPage() {
		return "newProject";
	}
	
	@GetMapping("/edit_project/{id}")
	public String editProjectPage() {
		return "newProject";
	}
	
	@GetMapping("/projects")
	public String viewProjectsPage() {
		return "newProject";
	}
	
	
	
}
