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

	List<User> users = new ArrayList<>();

	public void addUser(User user)
	{
		userRepository.save(user);
//		users.add(user);
	}
	public List<User> getAllUsers() {
		//		List<User> users = new ArrayList<>();
		//		userRepository.findAll().forEach(users::add);
//		return users;		//finds all the users and adds it to the users List and returns it
		List<User> users=new ArrayList<>();
		
		userRepository.findAll().forEach(users::add);	//for every found row, adds it to the list 'users'
		return users;
	}
	public User getUser(Long id)
	{
		//		List<User> users = new ArrayList<>();
		//		userRepository.findAll().forEach(users::add);		//users gets all the users
//		return users.stream().filter(u -> u.getName().equals(username)).findFirst().get();	//this filters teh users by same usernames
		return userRepository.findById(id).get();

	}
	public void DeleteAll()
	{
		userRepository.deleteAll();
	}
	public void updateUser(Long id, User newUser) 
	{
//		for(int i=0;i<users.size();i++)
//		{
//			User u=users.get(i);
//			if(u.getName().equals(username))
//			{
//				users.set(i,newUser);
//				return;
//			}
//		}
//		userRepository.deleteById(id);
		userRepository.save(newUser);
		
	}
	public void deleteUser(Long id) {
//		for(int i=0;i<users.size();i++)
//		{
//			if(users.get(i).getName().equals(username))
//			{
//				users.remove(i);
//			}
//		}
		userRepository.deleteById(id);
		
	}
} 
