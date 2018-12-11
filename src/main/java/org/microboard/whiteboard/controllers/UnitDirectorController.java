package org.microboard.whiteboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UnitDirectorController {

	@GetMapping("/unit_director/new_project")
	public String getNewProjectPage() {
		return "newProject";
	}
	
}
