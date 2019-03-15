package org.microboard.whiteboard.model.project.visitors;

import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.project.SoloProject;

public class EditPathGetter extends ProjectVisitor {
	private String result;
	
	
	@Override
	public void visit(SoloProject soloProject) {
		result = "/unit_director/edit_solo_project";
	}

	@Override
	public void visit(GroupProject groupProject) {
		result = "/unit_director/edit_group_project";
	}

	public String getResult() {
		return result;
	}
	
}
