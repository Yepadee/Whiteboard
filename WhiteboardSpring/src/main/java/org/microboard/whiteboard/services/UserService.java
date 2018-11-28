package org.microboard.whiteboard.services;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;		//finds all the users and adds it to the users List and returns it
	}
	
	public void addUser(User user) {
		userRepository.save(user);
		
	}
} 
