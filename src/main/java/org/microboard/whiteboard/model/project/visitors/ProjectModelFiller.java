package org.microboard.whiteboard.model.project.visitors;

import org.microboard.whiteboard.dto.project.GroupProjectDto;
import org.microboard.whiteboard.dto.project.SoloProjectDto;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.pojos.ProjectTemplateMaker;
import org.springframework.ui.Model;

public class ProjectModelFiller extends ProjectVisitor {
	private Model model;
//	private ProjectTemplateMaker ptm = new ProjectTemplateMaker();
	
	public ProjectModelFiller(Model model) {
		this.model = model;
	}
	
	@Override
	public void visit(SoloProject soloProject) {
//		SoloProjectDto editProject = ptm.getTemplate(soloProject);
		model.addAttribute("project", soloProject);
	}

	@Override
	public void visit(GroupProject groupProject) {
//		GroupProjectDto editProject = ptm.getTemplate(groupProject);
		model.addAttribute("project", groupProject);
	}

}
