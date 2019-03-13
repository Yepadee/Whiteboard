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
		return super.validate() && validateGroups();
	}
	
	private boolean validateGroups() {
		boolean valid = true;
		
		for (Group group1 : groups) {
			for (Group group2 : groups) {
				if (group1.equals(group2)) continue;
				
				boolean error = group1.getMembers().retainAll(group2.getMembers());
				
				//errorMsg += "Group \"" + \""
			}
		}
		
		return valid;
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
