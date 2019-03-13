package org.microboard.whiteboard.dto.project;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.dto.assessment.SoloAssessmentDto;

public class SoloProjectDto extends ProjectDto {
	private List<SoloAssessmentDto> assessments = new ArrayList<>();
	
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
		return assessments.isEmpty();
	}
}
