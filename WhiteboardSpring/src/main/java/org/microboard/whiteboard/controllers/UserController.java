package org.microboard.whiteboard.controllers;

import java.util.List;

import org.microboard.whiteboard.services.UserService;
import org.microboard.whiteboard.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/AddMockUsers")
	public void testAddUser() {
//		User u=new User();
//		u.setName("ABCD");
//		u.setPassword("12345");
		userService.addUser(new User("A","12345"));
		userService.addUser(new User("B","12345"));
//		userService.addUser(new User("C","12345"));
//		userService.addUser(new User("D","12345"));
//		userService.addUser(new User("E","12345"));
//		userService.addUser(new User("F","12345"));
	}
	
	@PostMapping("/AddUser")
	public void addUser(@RequestBody User newUser) {
		userService.addUser(newUser);
	}
	
	@PutMapping("/UpdateUser/{id}")
	public void updateUser(@RequestBody User newUser,@PathVariable Long id) {//change String username to String id so that names can be found using just id
		userService.updateUser(id,newUser);
	}
	
	@GetMapping("/ViewAllUsers")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/DeleteAllUsers")
	public void deleteAllUsers() {
		userService.DeleteAll();
	}
	@DeleteMapping("/DeleteUser/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
	
	@GetMapping("/GetUser/{id}")
	public User getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}
	
	
	@GetMapping("/users/tasks")
	public String getUserTasks(Model model) {
		User newUser = new User();
		newUser.setName("Alex");
		model.addAttribute("user", newUser);
		return "test";
	}
}
