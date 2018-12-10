package org.microboard.whiteboard.model.user.visitors;

import java.util.ArrayList;
import java.util.Collection;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;

public class UserRoleGetter extends UserVisitor {
	public static String STUDENT_ROLE = "STUDENT";
	public static String ASSESSOR_ROLE = "ASSESSOR";
	public static String UNIT_DIRECTOR_ROLE = "UNIT_DIRECTOR";

	private Collection<String> userRoles;
	
	public Collection<String> getResult() {
		return userRoles;
	}
	
	@Override
	public void visit(Student student) {
		userRoles = new ArrayList<>();
		userRoles.add(STUDENT_ROLE);
		
	}

	@Override
	public void visit(Assessor assessor) {
		userRoles = new ArrayList<>();
		userRoles.add(ASSESSOR_ROLE);
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		userRoles = new ArrayList<>();
		userRoles.add(UNIT_DIRECTOR_ROLE);
	}

}
