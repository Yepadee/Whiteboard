package org.microboard.whiteboard.dto.project;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.dto.assessment.GroupAssessmentDto;
import org.microboard.whiteboard.model.user.Group;
import org.microboard.whiteboard.model.user.User;

public class GroupProjectDto extends ProjectDto {
	private List<GroupAssessmentDto> assessments = new ArrayList<>();
	private List<Group> groups = new ArrayList<>();
	
	
	public List<GroupAssessmentDto> getAssessments() {
		return assessments;
	}
	
	public void setAssessments(List<GroupAssessmentDto> assessments) {
		this.assessments = assessments;
	}
	
	protected boolean validateAssessments() {
		boolean valid = true;
		int i = 1;
		for (GroupAssessmentDto assessment : assessments) {
			valid = valid && assessment.validate();
			errorMsg += "Assessment " + i + ": {" + assessment.getErrorMsg() + "} ";
			i ++;
		}
		
		return valid;
	}
	
	public boolean validate() {
		boolean validProject = super.validate();
		boolean validGroups = validateGroups();
		return validGroups && validProject;
	}
	
	private boolean validateGroups() {
		return validateGroupNames() && validateGroupMembers();
	}
	
	private boolean validateGroupNames() {
		int i = 0;
		boolean valid = true;
		for (Group group : groups) {
			i ++;
			if (group.getName().isEmpty()) {
				errorMsg += "Group " + i + " has not been named. \n";
				valid = false;
			}
		}
		
		return valid;
	}
	
	private boolean validateGroupMembers() {
		for (Group group1 : groups) {
			for (Group group2 : groups) {
				if (group1.equals(group2))
					continue;
				List<User> common = new ArrayList<User>(group1.getMembers());
				common.retainAll(group2.getMembers());

						
				if (!common.isEmpty()) {
					errorMsg += "Group \"" + group1.getName() + "\" and Group \"" + group2.getName() + "\" cannot contain the same users: " + common.toString() + ".";
					return false;
				}
			}

		}
		
		return true;
	}
	
	public boolean canDelete() {
		return assessments.isEmpty();
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
}
