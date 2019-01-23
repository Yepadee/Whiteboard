package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;

public class HomePageGetter extends UserVisitor {
	private String pathSuffix;
	
	
	public String getResult() {
		return pathSuffix;
	}
	
	@Override
	public void visit(Student student) {
		pathSuffix = "main_student";
	}

	@Override
	public void visit(Assessor assessor) {
		pathSuffix = "main_assessor";
		
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		pathSuffix = "main_unitDirector";
	}

}
