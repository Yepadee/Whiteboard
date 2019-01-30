package org.microboard.whiteboard.repositories.user;

import org.microboard.whiteboard.model.user.Assessor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AssessorRepository extends BaseUserRepository<Assessor> {
}