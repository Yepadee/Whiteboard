package org.microboard.whiteboard.services.user;

import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.repositories.user.UserRepository;
import org.microboard.whiteboard.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseUserService<User> {
	public User getLoggedInUser() {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User loggedInUser = userDetails.getUser();
		return loggedInUser;
	}
} 
