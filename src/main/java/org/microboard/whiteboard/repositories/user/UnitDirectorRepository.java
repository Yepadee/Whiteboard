package org.microboard.whiteboard.repositories.user;

import java.util.Optional;

import org.microboard.whiteboard.model.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UnitDirectorRepository extends CrudRepository<User, Long> {
	Optional<User> findByUserName(String userName);	
}
