package org.microboard.whiteboard.model.project.visitors;

import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.project.SoloProject;

public abstract class ProjectVisitor {
	public abstract void visit(SoloProject soloProject);
	public abstract void visit(GroupProject groupProject);
}
