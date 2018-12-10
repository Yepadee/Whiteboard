package org.microboard.whiteboard.controllers;

import java.util.Optional;

import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String getSignupForm()
	{
		return "signUp";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute(name="signupForm") Student user, Model model)
	{
		userService.addUser(user);
		return "signUp";

	}
	@GetMapping("/login")
	public String getLoginForm()
	{
		User user = new Student();
		user.setUserName("a");
		user.setPassword("1");
		
		User user1 = new UnitDirector();
		user1.setUserName("b");
		user1.setPassword("2");
		
		userService.addUser(user);
		userService.addUser(user1);
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute(name="loginForm") Student user, Model model)
	{
		String username=user.getUserName();
		String password=user.getPassword();
		Optional<User> maybeUser=userService.getByUserName(username);
		if(maybeUser.isPresent())
		{
			User foundUser = maybeUser.get();
			if(foundUser.getPassword().equals(password))
			{
				model.addAttribute("message", "Wellcomeeee");
			}
			else
				model.addAttribute("message","Password incorrect");
		}
		else
			model.addAttribute("message","User not found!!!");


		return "login";

	}
}
