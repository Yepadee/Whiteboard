package org.microboard.whiteboard.services.user;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends BaseUserService<Student> {
	public void changePerms(UnitDirector unitDirector) {
		if (unitDirector.getMyProjects().isEmpty()) {
			addUser(new Student(unitDirector));
		} else {
			throw new RuntimeException("Cannot demote a unit director with projects against them");
		}
	}
	
	public void changePerms(Assessor assessor) {
		addUser(new Student(assessor));
	}
} 