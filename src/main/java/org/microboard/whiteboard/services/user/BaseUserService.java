package org.microboard.whiteboard.services.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.repositories.user.BaseUserRepository;
import org.microboard.whiteboard.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseUserService<T extends User> {
	
	Logger logger = LoggerFactory.getLogger(BaseUserService.class);

	@Autowired
	protected BaseUserRepository<T> repository;

	public void addUser(T user) {
		repository.save(user);
		logger.info("Added user \"" + user.getUserName() + "\"");
	}

	public List<T> getAllUsers() {
		List<T> users = new ArrayList<>();
		repository.findAll().forEach(users::add);

		return users;
	}

	public Optional<T> getUser(Long id) {
		return repository.findById(id);
	}

	public void updateUser(T newUser) {
		repository.save(newUser);
	}

	public void deleteUser(Long id) {
		repository.deleteById(id);
	}

	public Optional<T> getByUserName(String name) {
		return repository.findByUserName(name);
	}
	
	public T getLoggedInUser() {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long userId = userDetails.getUser().getId();
		return getUser(userId).get();
	}
}