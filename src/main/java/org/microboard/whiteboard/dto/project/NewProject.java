package org.microboard.whiteboard.dto.project;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.user.Unit;

public abstract class NewProject {
	private String name;
	private String description;
	private Unit unit;
	protected String errorMsg = "";
	
	public NewProject() {}
	
	public NewProject(Project project) {
		String name = project.getName();
		String description = project.getDescription();
		Unit unit = project.getUnit();
		
		setName(name);
		setDescription(description);
		setUnit(unit);
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