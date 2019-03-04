package org.microboard.whiteboard.controllers;

import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/signup")
	public String getSignupForm() {
		return "server/signUp";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute(name = "newStudent") Student user, Model model) {
		userService.addUser(user);
		return "server/signUp";
	}

	@GetMapping("/login")
	public String getLoginForm(Model model, @RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "error", required = false) String error) {
		String message = "";
		if (logout != null) {
			message = "Logout Successful!";
		}
		if (error != null) {
			message = "Incorrect details. Please try again.";
		}
		model.addAttribute("message", message);
		return "server/index";
	}
	
	@Profile("dev")
	@GetMapping("/login_dev")
	public String getLoginFormDev(Model model, @RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "error", required = false) String error) {
		String message = "";
		if (logout != null) {
			message = "Logout Successful!";
		}
		if (error != null) {
			message = "Incorrect details. Please try again.";
		}
		model.addAttribute("message", message);
		return "server/login_dev";
	}
	
	@GetMapping("/access_denied")
	public String getAccessDeniedPage() {
		return "server/access_denied";
	}
	
	@GetMapping("/error")
	public String getErrorPage() {
		return "error";
	}
}
