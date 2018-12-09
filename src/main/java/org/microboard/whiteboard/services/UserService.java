package org.microboard.whiteboard.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	List<User> users = new ArrayList<>();

	public void addUser(User user)
	{
		userRepository.save(user);
		users.add(user);
	}
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}
	public User getUser(Long id)
	{
		return userRepository.findById(id).get();

	}
	public void DeleteAll()
	{
		userRepository.deleteAll();
	}
	public void updateUser(User newUser) 
	{
		userRepository.save(newUser);
		
	}
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
		
	}
	public Optional<User> getByName(String name)
	{
		return userRepository.findByName(name);
		
	}
} 
