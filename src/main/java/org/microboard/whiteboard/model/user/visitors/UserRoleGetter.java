package org.microboard.whiteboard.model.user.visitors;

import java.util.ArrayList;
import java.util.Collection;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;

public class UserRoleGetter extends UserVisitor {
	public static String ROLE_STUDENT = "ROLE_STUDENT";
	public static String ROLE_ASSESSOR = "ROLE_ASSESSOR";
	public static String ROLE_UNIT_DIRECTOR = "ROLE_UNIT_DIRECTOR";

	private Collection<String> userRoles;
	
	public Collection<String> getResult() {
		return userRoles;
	}
	
	@Override
	public void visit(Student student) {
		userRoles = new ArrayList<>();
		userRoles.add(ROLE_STUDENT);
		
	}

	@Override
	public void visit(Assessor assessor) {
		userRoles = new ArrayList<>();
		userRoles.add(ROLE_STUDENT);
		userRoles.add(ROLE_ASSESSOR);
		
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		userRoles = new ArrayList<>();
		userRoles.add(ROLE_ASSESSOR);
		userRoles.add(ROLE_UNIT_DIRECTOR);
	}

}
