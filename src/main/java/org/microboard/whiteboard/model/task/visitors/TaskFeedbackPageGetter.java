package org.microboard.whiteboard.model.task.visitors;

import org.microboard.whiteboard.model.task.GroupTask;
import org.microboard.whiteboard.model.task.SoloTask;
import org.springframework.ui.Model;

public class TaskFeedbackPageGetter extends TaskVisitor {
	private String result;
	private Model model;
	
	public TaskFeedbackPageGetter(Model model) {
		this.model = model;
	}
	
	public String getResult() {
		return result;
	}
	
	@Override
	public void visit(SoloTask soloTask) {
		result = "assessor/solo_feedback";
		model.addAttribute("task", soloTask);
		
	}

	@Override
	public void visit(GroupTask groupTask) {
		result = "assesspr/group_feedback";
		model.addAttribute("task", groupTask);
	}

}
