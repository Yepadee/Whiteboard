package org.microboard.whiteboard.model.task.visitors;

import org.microboard.whiteboard.model.task.GroupTask;
import org.microboard.whiteboard.model.task.SoloTask;

public class TaskUploadPathGen extends TaskVisitor {
	private String result = "";
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public void visit(SoloTask soloTask) {
		String path = System.getProperty("user.dir") + "/uploads/";
		path += soloTask.getSoloAssessment().getProject().getUnit().getUnitCode() + "/";
		path += soloTask.getSoloAssessment().getProject().getId() + "/";
		path += soloTask.getAccountable().getUserName() + "/";
		path += soloTask.getAssessment().getId() + "/";
		result=path;
	}

	@Override
	public void visit(GroupTask groupTask) {
		String path = System.getProperty("user.dir") + "/uploads/";
		path += groupTask.getGroupAssessment().getProject().getUnit().getUnitCode() + "/";
		path += groupTask.getGroupAssessment().getProject().getId() + "/";
		path += groupTask.getAccountable().getName() + "/";
		path += groupTask.getAssessment().getId() + "/";
		result=path;
	}

}
