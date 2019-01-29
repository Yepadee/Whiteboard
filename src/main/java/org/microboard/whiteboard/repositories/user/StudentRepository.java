package org.microboard.whiteboard.repositories.user;

import org.microboard.whiteboard.model.user.Student;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StudentRepository extends BaseUserRepository<Student> {
}