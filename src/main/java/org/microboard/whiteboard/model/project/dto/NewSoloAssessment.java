package org.microboard.whiteboard.model.project.dto;

import java.util.ArrayList;
import java.util.List;

public class NewSoloAssessment {
	private String name;
	private String description;
	private List<MarkerStudents> markerStudents = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<MarkerStudents> getMarkerStudents() {
		return markerStudents;
	}
	public void setMarkerStudents(List<MarkerStudents> markerStudents) {
		this.markerStudents = markerStudents;
	}
}
