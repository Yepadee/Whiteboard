package org.microboard.whiteboard.model.project.visitors;

import java.io.File;
import java.util.List;

import org.microboard.whiteboard.model.assessment.GroupAssessment;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.user.Group;
import org.microboard.whiteboard.model.user.User;

public class ProjectFolderCreator extends ProjectVisitor {

	@Override
	public void visit(SoloProject project) {
		String path = System.getProperty("user.dir") + "/uploads/";
		path += project.getUnit().getUnitCode()+"/";
		path += project.getName() + "/";
		
		List<User> users = project.getUnit().getCohort();
		for (User user : users) {
			String userPath = path + user.getUserName() + "/";
			for (SoloAssessment assessment : project.getAssessments()) {
				new File(userPath + assessment.getName() + "/feedback/").mkdirs();
			}
		}
	}

	@Override
	public void visit(GroupProject project) {
		System.out.println("Called on project " + project.getName());
		String path = System.getProperty("user.dir") + "/uploads/";
		path += project.getUnit().getUnitCode()+"/";
		path += project.getName() + "/";
		List<Group> groups = project.getGroups();
		for (Group group : groups) {
			String groupPath = path + group.getName() + "/";
			for (GroupAssessment assessment : project.getAssessments()) {
				String feedbackPath = groupPath + assessment.getName() + "/feedback/";
				for (User user : group.getMembers()) {
					System.out.println("Created folder " + feedbackPath + user.getName());
					new File(feedbackPath + user.getName() + "/").mkdirs();
				}
			}
		}
	}

}
