package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;

public class UserSetUnitDirectorValidator extends UserVisitor {
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
		valid = true;
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		valid = false;
	}

}
