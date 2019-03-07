package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;

public class UserSetAssessorValidator extends UserVisitor {
	boolean valid;
	
	public boolean getResult() {
		return valid;
	}
	
	@Override
	public void visit(Student student) {
		valid = true;
	}

	@Override
	public void visit(Assessor assessor) {
		valid = false;
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		unitDirector.getMyProjects().isEmpty();
	}

}
