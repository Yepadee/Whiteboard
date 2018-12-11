package org.microboard.whiteboard.security;

import java.util.Collection;
import java.util.Optional;

import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.UserRoleGetter;
import org.microboard.whiteboard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> maybeUser = userRepository.findByUserName(username);
		if (maybeUser.isPresent()) {
			User user = maybeUser.get();
			UserRoleGetter roleGetter = new UserRoleGetter();
			user.accept(roleGetter);
			Collection<String> userRoles = roleGetter.getResult();
			return new CustomUserDetails(user, userRoles);
		} else {
			throw new UsernameNotFoundException("No user present with username \"" + username + "\".");
		}
	}

}
