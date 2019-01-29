package org.microboard.whiteboard.repositories.user;

import java.util.Optional;

import org.microboard.whiteboard.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseUserRepository<T extends User> extends CrudRepository<T, Long> {
	Optional<T> findByUserName(String userName);	
}
