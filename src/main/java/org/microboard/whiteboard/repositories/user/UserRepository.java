package org.microboard.whiteboard.repositories.user;

import java.util.Optional;

import org.microboard.whiteboard.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUserName(String userName);	
}
