package org.microboard.whiteboard.model.project.dto;

import java.util.ArrayList;
import java.util.List;

public class NewSoloAssessment {
	private String name;
	private String description;
	private List<MarkerDto> markerDtos = new ArrayList<>();
	private String errorMsg = "";
	
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
	public List<MarkerDto> getMarkerDtos() {
		return markerDtos;
	}
	public void setMarkerDto(List<MarkerDto> markerDtos) {
		this.markerDtos = markerDtos;
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
}
