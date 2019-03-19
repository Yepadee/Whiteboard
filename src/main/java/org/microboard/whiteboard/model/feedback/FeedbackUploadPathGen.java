package org.microboard.whiteboard.model.feedback;

import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskUploadPathGen;

public class FeedbackUploadPathGen {

	public String getFeedbackPath(Feedback feedback) {
		
		Task task = feedback.getTask();
		TaskUploadPathGen taskUploadPathGen = new TaskUploadPathGen();
		task.accept(taskUploadPathGen);
		String path = taskUploadPathGen.getResult();

		return path + "feedback/";
	}

}
