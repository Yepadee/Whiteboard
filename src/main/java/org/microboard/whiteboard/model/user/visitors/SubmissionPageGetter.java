package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.springframework.ui.Model;

public class SubmissionPageGetter extends UserVisitor {
	private String template;
	private Model model;
	
	public SubmissionPageGetter(Model model) {
		this.model = model;
	}
	
	public String getResult() {
		return template;
	}
	
	@Override
	public void visit(Student student) {
		template = "submission_student";
		model.addAttribute("user", student);
	}

	@Override
	public void visit(Assessor assessor) {
		template = "submission_assessor";
		model.addAttribute("user", assessor);
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		template = "submission_unitDirector";
		model.addAttribute("user", unitDirector);
	}

}
