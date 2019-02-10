package org.microboard.whiteboard.dto.assessment;

import java.util.Date;

public abstract class NewAssessment {
	private long id;
	private String name;
	private String description;
	private Date studentDeadline;
	private Date markerDeadline;
	private int weight;

	
	protected String errorMsg = "";
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public boolean validate() {
		boolean valid = true;
		
		if (name == null) {
			valid = false;
			errorMsg += "Assessment name field cannot be empty.\n";
		} else {
			if (name.length() == 0)  {
				valid = false;
				errorMsg += "Assessment name field cannot be empty.\n";
			}
		}
		
		if (description == null) {
			valid = false;
			errorMsg += "Assessment description field cannot be empty.\n";
		} else {
			if (description.length() == 0)  {
				valid = false;
				errorMsg += "Assessment description field cannot be empty.\n";
			}
		}
		
		return valid;
	}
	
	abstract protected boolean validateMarkers();
}
