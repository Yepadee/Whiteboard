package org.microboard.whiteboard.repositories.user;

import org.microboard.whiteboard.model.user.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends BaseUserRepository<User> {
}
