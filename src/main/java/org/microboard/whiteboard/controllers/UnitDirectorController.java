package org.microboard.whiteboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UnitDirectorController {

	@GetMapping("/unitDirector")
	public String accessDenied() {
		return "main_unitDirector";
	}
	
}
