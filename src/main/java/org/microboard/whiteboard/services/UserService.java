package org.microboard.whiteboard.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.controllers.LoginController;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.repositories.UserRepository;
import org.microboard.whiteboard.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public UserService() {}
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void addUser(User user) {
		userRepository.save(user);
		logger.info("Added user \"" + user.getUserName() + "\"");
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);

		return users;
	}

	public Optional<User> getUser(Long id) {
		return userRepository.findById(id);

	}

	public void DeleteAll() {
		userRepository.deleteAll();
	}

	public void updateUser(User newUser) {
		userRepository.save(newUser);

	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);

	}

	public Optional<User> getByUserName(String name) {
		return userRepository.findByUserName(name);

	}
	
	public Optional<User> getLoggedInUser() {
		Optional<User> maybeLoggedInUser = Optional.empty();
		try { 
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User loggedInUser = userDetails.getUser();
			maybeLoggedInUser = Optional.of(loggedInUser);
		} catch (Exception e) {}
		
		return maybeLoggedInUser;
	}
} 
