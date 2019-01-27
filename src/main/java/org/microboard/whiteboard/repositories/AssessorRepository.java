package org.microboard.whiteboard.repositories;

import java.util.Optional;

import org.microboard.whiteboard.model.user.Assessor;
import org.springframework.data.repository.CrudRepository;

public interface AssessorRepository extends CrudRepository<Assessor, Long> {
	Optional<Assessor> findByUserName(String userName);
}