package org.microboard.whiteboard.controllers;

import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.HomePageGetter;
import org.microboard.whiteboard.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


	@GetMapping("/")
	private String getMainPage() {
		return "redirect:user/outstanding_tasks";
	}
	
}
