package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;

public class HomePageGetter extends UserVisitor {
	private String homePage;
	
	
	public String getResult() {
		return homePage;
	}
	
	@Override
	public void visit(Student student) {
		homePage = "main_student";
	}

	@Override
	public void visit(Assessor assessor) {
		homePage = "main_student";
		
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		homePage = "main_unitDirector";
	}

}
