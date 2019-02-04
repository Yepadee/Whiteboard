package org.microboard.whiteboard.model.project.dto;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.User;

public class MarkerDto {
	private Assessor marker;
	private List<UserDto> toMark = new ArrayList<>();
	
	public Assessor getMarker() {
		return marker;
	}
	public void setMarker(Assessor marker) {
		this.marker = marker;
	}
	public List<UserDto> getToMark() {
		return toMark;
	}
	public void setToMark(List<UserDto> toMark) {
		this.toMark = toMark;
	}
	
}
