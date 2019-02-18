package org.microboard.whiteboard.dto.project;

import org.microboard.whiteboard.model.user.Unit;

public abstract class ProjectDto {
	private Long id;
	private String name;
	private String description;
	private Unit unit;
	protected String errorMsg = "";
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		errorMsg += "Error in project form:\n";
		
		if (unit.getId() == null) {
			valid = false;
			errorMsg += "No unit selected.\n";
		}
		
		if (name == null) {
			valid = false;
			errorMsg += "Project name field cannot be empty.\n";
		} else {
			if (name.length() == 0)  {
				valid = false;
				errorMsg += "Project name field cannot be empty.\n";
			}
		}
		
		boolean assessmentsValid = validateAssessments();
		return valid && assessmentsValid;
	}
	protected abstract boolean validateAssessments();

}
