package org.microboard.whiteboard.repositories.user;

import java.util.List;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AssessorRepository extends BaseUserRepository<Assessor> {
	@Query("SELECT u FROM User u WHERE perms = 'assessor'")
    public List<User> getOnlyAssessors();
}