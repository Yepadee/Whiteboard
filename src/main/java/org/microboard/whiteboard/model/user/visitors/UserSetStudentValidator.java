package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;

public class UserSetStudentValidator extends UserSetAssessorValidator {
	boolean valid;
	
	public boolean getResult() {
		return valid;
	}
	
	@Override
	public void visit(Student student) {
		valid = false;
	}

	@Override
	public void visit(Assessor assessor) {
		valid = assessor.getTaskFeedback().isEmpty();
	}

}
