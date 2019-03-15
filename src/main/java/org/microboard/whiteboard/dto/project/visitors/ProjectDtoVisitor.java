package org.microboard.whiteboard.dto.project.visitors;

import org.microboard.whiteboard.dto.project.GroupProjectDto;
import org.microboard.whiteboard.dto.project.SoloProjectDto;

public abstract class ProjectDtoVisitor {
	public abstract void visit(SoloProjectDto soloProjectDto);
	public abstract void visit(GroupProjectDto soloProjectDto);
}
