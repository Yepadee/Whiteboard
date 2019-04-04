package org.microboard.whiteboard.model.task.visitors;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.task.GroupTask;
import org.microboard.whiteboard.model.task.SoloTask;

public class TaskProjectGetter extends TaskVisitor {
	
	private Project result;
	
	public Project getResult() {
		return result;
	}
	
	@Override
	public void visit(SoloTask soloTask) {
		result = soloTask.getSoloAssessment().getProject();
		
	}

	@Override
	public void visit(GroupTask groupTask) {
		result = groupTask.getGroupAssessment().getProject();
	}

}
