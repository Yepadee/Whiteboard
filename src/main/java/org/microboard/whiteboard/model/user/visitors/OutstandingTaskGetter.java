package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;

public class OutstandingTaskGetter extends UserVisitor {
	private String result;
	
	public String getResult() {
		return result;
	}
	
	@Override
	public void visit(Student student) {
		result = "student/outstanding_tasks";
	}

	@Override
	public void visit(Assessor assessor) {
		result = "assessor/outstanding_tasks";
		
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		result = "unit_director/outstanding_tasks";
		
	}

}
