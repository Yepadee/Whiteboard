package org.microboard.whiteboard.dto.assessment;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.dto.user.MarkerDto;
import org.microboard.whiteboard.dto.user.MarkerUserDto;

public class NewSoloAssessment extends NewAssessment {
	private List<MarkerUserDto> markerDtos = new ArrayList<>();
	
	public NewSoloAssessment() {}
	
	public List<MarkerUserDto> getMarkerDtos() {
		return markerDtos;
	}

	public void setMarkerDtos(List<MarkerUserDto> markerDtos) {
		this.markerDtos = markerDtos;
	}

	@Override
	protected boolean validateMarkers() {
		// TODO Auto-generated method stub
		return true;
	}
}
