package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;

public abstract class UserVisitor {
	public abstract void visit(Student student);
	public abstract void visit(Assessor assessor);
	public abstract void visit(UnitDirector unitDirector);
}
