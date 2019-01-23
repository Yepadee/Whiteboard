package org.microboard.whiteboard.controllers;

import java.util.Optional;

import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unit_director")
public class UnitDirectorController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/new_project")
	public String getNewProjectPage(Model model) {
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
	
	@ModelAttribute("user")
	public UnitDirector getUnitDirector() {
	   return (UnitDirector) userService.getLoggedInUser().get();
	}
	
}
