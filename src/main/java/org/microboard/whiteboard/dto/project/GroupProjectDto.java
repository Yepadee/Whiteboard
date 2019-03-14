package org.microboard.whiteboard.dto.project;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.dto.assessment.GroupAssessmentDto;
import org.microboard.whiteboard.dto.project.visitors.ProjectDtoVisitor;
import org.microboard.whiteboard.dto.user.MarkerGroupDto;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.user.Group;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.pojos.ProjectEditApplyer;

public class GroupProjectDto extends ProjectDto<GroupProject> {
	private List<GroupAssessmentDto> assessments = new ArrayList<>();
	private List<Group> groups = new ArrayList<>();
	
	public void changeUnit() {
		for (GroupAssessmentDto assessment : getAssessments()) {
			for (MarkerGroupDto markerDto : assessment.getGroupMarkerDtos()) {
				markerDto.setToMark(new ArrayList<>());
			}
		}
	}
	
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

	@Override
	public void accept(ProjectDtoVisitor v) {
		v.visit(this);
	}
	
	@Override
	public void removeAssessment(int index) {
		assessments.remove(index);
	}
	
	@Override
	public void addAssessment() {
		assessments.add(new GroupAssessmentDto());
	}

	@Override
	public void addMarker(int index) {
		assessments.get(index).getGroupMarkerDtos().add(new MarkerGroupDto());
	}
	
	@Override
	public void removeMarker(int assessmentIndex, int markerIndex) {
		assessments.get(assessmentIndex).getGroupMarkerDtos().remove(markerIndex);
	}
	
	public void addGroup() {
		groups.add(new Group());
	}
	
	public void removeGroup(int index) {
		groups.remove(index);
	}

	@Override
	public GroupProject toProject() {
		GroupProject newProject = new GroupProject();
		ProjectEditApplyer pea = new ProjectEditApplyer();
		pea.applyEdits(newProject, this);
		return newProject;
	}
	
}
