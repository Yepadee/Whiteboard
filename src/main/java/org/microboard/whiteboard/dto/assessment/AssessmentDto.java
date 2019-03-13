package org.microboard.whiteboard.dto.assessment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.microboard.whiteboard.dto.user.MarkerDto;
import org.microboard.whiteboard.model.user.Assessor;

public abstract class AssessmentDto {
	private Long id;
	private String name;
	private String description;
	private Date studentDeadline;
	private Date markerDeadline;
	private int weight;

	
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
		
		
		if (studentDeadline == null) {
			valid = false;
			errorMsg += "Student deadline not set.\n";
		}
		
		if (markerDeadline == null) {
			valid = false;
			errorMsg += "Marker deadline not set.\n";
		}
		
		
		if (studentDeadline != null && markerDeadline != null) {

			
			if (markerDeadline.before(new Date())) {
				valid = false;
				errorMsg += "Marker deadline must be in the future.\n";
			}
			
			if (studentDeadline.before(new Date())) {
				valid = false;
				errorMsg += "Student deadline must be in the future.\n";
			}
			
			if (! markerDeadline.after(studentDeadline)) {
				valid = false;
				errorMsg += "Marker deadline must be after student deadline.\n";
			}
		}
		return validateMarkers() && valid;
	}
	
	protected boolean validateMarkers() {
		boolean valid = true;
		Map<Assessor, Integer> markerCount = new HashMap<>();
		for (MarkerDto markerDto : getMarkerDtos()) {
			Assessor assessor = markerDto.getMarker();
			if (markerCount.containsKey(assessor)) {
				int count = markerCount.get(assessor);
				markerCount.put(assessor, count + 1);
			} else {
				markerCount.put(assessor, 1);
			}
			
		}
		
		for (Assessor assessor : markerCount.keySet()) {
			if (markerCount.get(assessor) > 1) {
				valid = false;
				errorMsg += assessor.getUserName() + " is assigned as a marker more than once.";
			}
		}
		
		
		return valid;
	}
	
	protected abstract List<MarkerDto> getMarkerDtos();
}
