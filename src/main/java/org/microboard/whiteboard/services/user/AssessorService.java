package org.microboard.whiteboard.services.user;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.springframework.stereotype.Service;

@Service
public class AssessorService extends BaseUserService<Assessor> {
	public void changePerms(UnitDirector unitDirector) {
		if (unitDirector.getMyProjects().isEmpty()) {
			addUser(new Assessor(unitDirector));
		} else {
			throw new RuntimeException("Cannot demote a unit director with projects against them");
		}
	}
	
	public void changePerms(User student) {
		addUser(new Assessor(student));
	}
} 
