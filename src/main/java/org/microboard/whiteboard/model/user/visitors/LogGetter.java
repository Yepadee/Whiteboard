package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;

public class LogGetter extends UserVisitor {
	private String result;
	
	public String getResult() {
		return result;
	}
	
	@Override
	public void visit(Student student) {
		result = "student/log";
	}

	@Override
	public void visit(Assessor assessor) {
		result = "assessor/log"; //TODO
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		result = "unit_director/log";
	}

}
