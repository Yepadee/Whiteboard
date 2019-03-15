package org.microboard.whiteboard.dto.project;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.dto.project.visitors.ProjectDtoVisitor;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.model.user.UnitDirector;

public abstract class ProjectDto<T extends Project> {
	private Long id;
	private String name;
	private String description;
	private Unit unit;
	protected String errorMsg = "";
	private List<UnitDirector> helpers = new ArrayList<>();
	
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
	
	public abstract void changeUnit();
	
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public boolean validate() {
		boolean valid = true;
		errorMsg += "> ";

		if (name == null) {
			valid = false;
			errorMsg += "Project name field cannot be empty.\n";
		} else {
			if (name.length() == 0)  {
				valid = false;
				errorMsg += "Project name field cannot be empty.\n";
			}
		}

		if (unit == null) {
			valid = false;
			errorMsg += "No unit selected.\n";
		} else if (unit.getId() == null) {
			valid = false;
			errorMsg += "No unit selected.\n";
		}

		

		boolean assessmentsValid = validateAssessments();
		return valid && assessmentsValid;
	}
	protected abstract boolean validateAssessments();

	public List<UnitDirector> getHelpers() {
		return helpers;
	}

	public void setHelpers(List<UnitDirector> helpers) {
		this.helpers = helpers;
	}
	
	public abstract void accept(ProjectDtoVisitor v);

	public abstract void addAssessment();
	
	public abstract void removeAssessment(int index);
	
	public abstract void addMarker(int index);
	
	public abstract void removeMarker(int assessmentIndex, int markerIndex);

	public abstract boolean canDelete();
	
	public abstract T toProject();
}
