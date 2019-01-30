package org.microboard.whiteboard.model.project.builder;

import java.util.List;

public class SoloProjectTemplate extends ProjectTemplate {
	private List<SoloAssessmentTemplate> assessments;

	public List<SoloAssessmentTemplate> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<SoloAssessmentTemplate> assessments) {
		this.assessments = assessments;
	}

}
