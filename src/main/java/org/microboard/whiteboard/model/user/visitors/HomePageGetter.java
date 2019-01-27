package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.springframework.ui.Model;

public class HomePageGetter extends UserVisitor {
	private String template;
	private Model model;
	
	public HomePageGetter(Model model) {
		this.model = model;
	}
	
	public String getResult() {
		return template;
	}
	
	@Override
	public void visit(Student student) {
		template = "main_student";
		model.addAttribute("user", student);
	}

	@Override
	public void visit(Assessor assessor) {
		template = "main_assessor";
		model.addAttribute("user", assessor);
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		template = "main_unitDirector";
		model.addAttribute("user", unitDirector);
	}

}
