package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;

public class HeaderGetter extends UserVisitor {
	private String result;
	
	public String getResult() {
		return result;
	}
	
	@Override
	public void visit(Student student) {
		result = "page_elements/header_student";
	}

	@Override
	public void visit(Assessor assessor) {
		result = "page_elements/header_student"; //TODO
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		result = "page_elements/header_unit_director";
	}

}
