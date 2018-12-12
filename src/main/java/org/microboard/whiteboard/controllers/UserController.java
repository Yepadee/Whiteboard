package org.microboard.whiteboard.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.HomePageGetter;
import org.microboard.whiteboard.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.microboard.whiteboard.model.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	private String getMainPage() {
		return "redirect:home";
	}
	
	@GetMapping("/home")
	public String getTaskPage(Model model) {
		Optional<User> maybeLoggedInUser = userService.getLoggedInUser();
		if (maybeLoggedInUser.isPresent()) {
			User user = maybeLoggedInUser.get();
			List<Task> tasks = new ArrayList<Task>();
			tasks.addAll(user.getTasks());
			
			model.addAttribute("tasks", tasks);
			model.addAttribute("user", user);

			//Get the correct page to visit depending on user type.
			//Pages returned can be changed in "mode/user/visitors/HomePageGetter"
			HomePageGetter homePageGetter = new HomePageGetter();
			user.accept(homePageGetter);
			return homePageGetter.getResult();
		} else {
			return "redirect:login";
		}
		
	}
}

