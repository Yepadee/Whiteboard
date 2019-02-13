package org.microboard.whiteboard.dto.project;

import org.microboard.whiteboard.model.user.Unit;

public abstract class ProjectDto {
	private long id;
	private String name;
	private String description;
	private Unit unit;
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
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public boolean validate() {
		boolean valid = true;
		errorMsg += "Error creating new project:\n";
		if (name == null) {
			valid = false;
			errorMsg += "Project name field cannot be empty.\n";
		} else {
			if (name.length() == 0)  {
				valid = false;
				errorMsg += "Project name field cannot be empty.\n";
			}
		}
		
		if (description == null) {
			valid = false;
			errorMsg += "Project description field cannot be empty.\n";
		} else {
			if (description.length() == 0)  {
				valid = false;
				errorMsg += "Project description field cannot be empty.\n";
			}
		}
		
		if (unit == null) {
			valid = false;
			errorMsg += "No unit selected.\n";
		}
		boolean assessmentsValid = validateAssessments();
		return valid && assessmentsValid;
	}
	protected abstract boolean validateAssessments();

}
