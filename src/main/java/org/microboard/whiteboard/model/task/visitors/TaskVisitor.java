package org.microboard.whiteboard.model.task.visitors;

import org.microboard.whiteboard.model.task.GroupTask;
import org.microboard.whiteboard.model.task.SoloTask;

public abstract class TaskVisitor {
	public abstract void visit(SoloTask soloTask);
	public abstract void visit(GroupTask groupTask);
}
