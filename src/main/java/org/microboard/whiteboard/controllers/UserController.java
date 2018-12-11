package org.microboard.whiteboard.controllers;

import java.util.ArrayList;
import java.util.List;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.HomePageGetter;
import org.microboard.whiteboard.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.task.GroupTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@GetMapping("/access_denied")
	public String getAccessDeniedPage() {
		return "access_denied";
	}
	
	@GetMapping("/error")
	public String getErrorPage() {
		return "error";
	}
	
	@GetMapping("/home")
	public String getMainTasks(Model model) {
		User user = userService.getLoggedInUser();
		
		//Tests
		List<Task> tasks = new ArrayList<>();
		SoloTask x = new SoloTask();
		x.setId(100);
		x.setStatus("Marks Released");
		tasks.add(x);
		GroupTask y = new GroupTask();
		y.setId(101);
		y.setStatus("Not active");
		tasks.add(y);
		
		model.addAttribute("tasks", tasks);
		model.addAttribute("user", user);

		//Get the correct page to visit depending on user type.
		//Pages returned can be changed in "mode/user/visitors/HomePageGetter"
		HomePageGetter homePageGetter = new HomePageGetter();
		user.accept(homePageGetter);
		return homePageGetter.getResult();
	}
}

