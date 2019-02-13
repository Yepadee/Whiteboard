package org.microboard.whiteboard.dto.project;

import java.util.ArrayList;
import java.util.List;
import org.microboard.whiteboard.dto.assessment.NewSoloAssessment;

public class SoloProjectDto extends ProjectDto {
	private List<NewSoloAssessment> assessments = new ArrayList<>();
	
	public List<NewSoloAssessment> getAssessments() {
		return assessments;
	}
	public void setAssessments(List<NewSoloAssessment> assessments) {
		this.assessments = assessments;
	}
	
	protected boolean validateAssessments() {
		boolean valid = true;
		int i = 1;
		for (NewSoloAssessment assessment : assessments) {
			valid = valid && assessment.validate();
			errorMsg += "Assessment " + i + ": {" + assessment.getErrorMsg() + "}";
			i ++;
		}
		
		return valid;
	}
}
