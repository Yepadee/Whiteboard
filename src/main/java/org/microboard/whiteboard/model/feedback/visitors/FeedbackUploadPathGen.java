package org.microboard.whiteboard.model.feedback.visitors;

import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.feedback.GroupMemberFeedback;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskUploadPathGen;

public class FeedbackUploadPathGen extends FeedbackVisitor {
	private String result = "";
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public void visit(Feedback feedback) {
		
		Task task = feedback.getTask();
		TaskUploadPathGen taskUploadPathGen = new TaskUploadPathGen();
		task.accept(taskUploadPathGen);
		String path = taskUploadPathGen.getResult();

		result = path + "feedback/";
	}

	@Override
	public void visit(GroupMemberFeedback groupMemberFeedback) {
		result = System.getProperty("user.dir") + "/uploads/";
	}

}
