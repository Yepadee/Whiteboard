package org.microboard.whiteboard.model.project.builder;

import java.util.List;

public class StudentMarkers {
	private int studentId;
	private List<Integer> markerIds;
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public List<Integer> getMarkerIds() {
		return markerIds;
	}
	public void setMarkerIds(List<Integer> markerIds) {
		this.markerIds = markerIds;
	}
	
}
