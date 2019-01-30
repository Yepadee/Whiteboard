package org.microboard.whiteboard.model.project.builder;

import java.util.Date;

public abstract class AssessmentTemplate {
	private String name;
	private String description;
	private Date studentDeadline;
	private Date markerDeadline;
	private int weight;
	
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
	public Date getStudentDeadline() {
		return studentDeadline;
	}
	public void setStudentDeadline(Date studentDeadline) {
		this.studentDeadline = studentDeadline;
	}
	public Date getMarkerDeadline() {
		return markerDeadline;
	}
	public void setMarkerDeadline(Date markerDeadline) {
		this.markerDeadline = markerDeadline;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
}
