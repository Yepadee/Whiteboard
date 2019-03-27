package org.microboard.whiteboard.dto.project.visitors;

import java.util.List;

import org.microboard.whiteboard.dto.project.GroupProjectDto;
import org.microboard.whiteboard.dto.project.SoloProjectDto;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.pojos.ProjectEditApplyer;

public class ProjectEditFiller extends ProjectDtoVisitor {
	private Project project;
	private ProjectEditApplyer editApplyer = new ProjectEditApplyer();
	
	public ProjectEditFiller(Project project) {
		this.project = project;
	}
	
	public List<String> getEditSummary() {
		return editApplyer.getEditSummary();
	}
	
	@Override
	public void visit(SoloProjectDto soloProjectDto) {
		editApplyer.applyEdits((SoloProject) project, soloProjectDto);
	}

	@Override
	public void visit(GroupProjectDto groupProjectDto) {
		editApplyer.applyEdits((GroupProject) project, groupProjectDto);
	}

}
