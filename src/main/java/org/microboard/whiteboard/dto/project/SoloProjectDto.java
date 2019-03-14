package org.microboard.whiteboard.dto.project;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.dto.assessment.SoloAssessmentDto;
import org.microboard.whiteboard.dto.project.visitors.ProjectDtoVisitor;
import org.microboard.whiteboard.dto.user.MarkerUserDto;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.pojos.ProjectEditApplyer;

public class SoloProjectDto extends ProjectDto<SoloProject> {
	private List<SoloAssessmentDto> assessments = new ArrayList<>();
	
	public void changeUnit() {
		for (SoloAssessmentDto assessment : getAssessments()) {
			for (MarkerUserDto markerDto : assessment.getSoloMarkerDtos()) {
				markerDto.setToMark(new ArrayList<>());
			}
		}
	}
	
	public List<SoloAssessmentDto> getAssessments() {
		return assessments;
	}
	public void setAssessments(List<SoloAssessmentDto> assessments) {
		this.assessments = assessments;
	}
	
	protected boolean validateAssessments() {
		boolean valid = true;
		int i = 1;
		for (SoloAssessmentDto assessment : assessments) {
			valid = valid && assessment.validate();
			errorMsg += "Assessment " + i + ": {" + assessment.getErrorMsg() + "} ";
			i ++;
		}
		
		return valid;
	}
	
	public boolean canDelete() {
		boolean canDelete = assessments.isEmpty();
		if (! canDelete) {
			errorMsg = "You must delete all of this project's assessments before you may delete this project.";
		}
		return canDelete;
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
		assessments.add(new SoloAssessmentDto());
	}

	@Override
	public void addMarker(int index) {
		getAssessments().get(index).getSoloMarkerDtos().add(new MarkerUserDto());
	}

	@Override
	public void removeMarker(int assessmentIndex, int markerIndex) {
		getAssessments().get(assessmentIndex).getSoloMarkerDtos().remove(markerIndex);
	}

	@Override
	public SoloProject toProject() {
		SoloProject newProject = new SoloProject();
		ProjectEditApplyer pea = new ProjectEditApplyer();
		pea.applyEdits(newProject, this);
		return newProject;
	}

}
