package org.microboard.whiteboard.repositories.user;

import org.microboard.whiteboard.model.user.UnitDirector;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UnitDirectorRepository extends BaseUserRepository<UnitDirector> {
}
