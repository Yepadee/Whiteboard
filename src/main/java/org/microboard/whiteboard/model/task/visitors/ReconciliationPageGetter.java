package org.microboard.whiteboard.model.task.visitors;

import org.microboard.whiteboard.model.task.GroupTask;
import org.microboard.whiteboard.model.task.SoloTask;
import org.springframework.ui.Model;

public class ReconciliationPageGetter extends TaskVisitor {

	private Model model;
	private String reconciliationPage;
	
	public ReconciliationPageGetter(Model model) {
		this.model = model;
	}
	
	public String getResult() {
		return reconciliationPage;
	}
	
	@Override
	public void visit(SoloTask soloTask) {
		reconciliationPage = "unit_director/solo_reconciliation";
		model.addAttribute("task", soloTask);
	}

	@Override
	public void visit(GroupTask groupTask) {
		reconciliationPage = "unit_director/group_reconciliation";
		model.addAttribute("task", groupTask);
	}

}
