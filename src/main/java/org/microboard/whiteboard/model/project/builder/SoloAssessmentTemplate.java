package org.microboard.whiteboard.model.project.builder;

import java.util.List;

public class SoloAssessmentTemplate extends AssessmentTemplate {
	private List<StudentMarkers> studentMarkers;

	public List<StudentMarkers> getStudentMarkers() {
		return studentMarkers;
	}

	public void setStudentMarkers(List<StudentMarkers> studentMarkers) {
		this.studentMarkers = studentMarkers;
	}
}
