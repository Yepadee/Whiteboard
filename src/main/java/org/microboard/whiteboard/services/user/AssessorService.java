package org.microboard.whiteboard.services.user;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.springframework.stereotype.Service;

@Service
public class AssessorService extends BaseUserService<Assessor> {	
	public void changePerms(User user) {
		addUser(new Assessor(user));
	}
} 
