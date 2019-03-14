package org.microboard.whiteboard.model.project.visitors;

import java.io.File;
import java.util.List;

import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.user.User;

public class ProjectFolderCreator extends ProjectVisitor {

	@Override
	public void visit(SoloProject project) {
		String path = System.getProperty("user.dir") + "/uploads/";
		path += Long.toString(project.getUnit().getId())+"/";
		List<User> users = project.getUnit().getCohort();
		for (User user : users) {
			String userPath = path + user.getUserName() + "/";
			userPath += project.getName() + "/";
			for (SoloAssessment assessment : project.getAssessments()) {
				new File(userPath + assessment.getName() + "/feedback/").mkdirs();
			}
		}
	}

	@Override
	public void visit(GroupProject groupProject) {
		// TODO Auto-generated method stub
		
	}

}
