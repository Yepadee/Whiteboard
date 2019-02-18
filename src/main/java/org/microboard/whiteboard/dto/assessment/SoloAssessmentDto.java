package org.microboard.whiteboard.dto.assessment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.microboard.whiteboard.dto.user.MarkerUserDto;
import org.microboard.whiteboard.model.user.Assessor;

public class SoloAssessmentDto extends AssessmentDto {
	private List<MarkerUserDto> markerDtos = new ArrayList<>();
	
	public SoloAssessmentDto() {}
	
	public List<MarkerUserDto> getMarkerDtos() {
		return markerDtos;
	}

	public void setMarkerDtos(List<MarkerUserDto> markerDtos) {
		this.markerDtos = markerDtos;
	}

	@Override
	protected boolean validateMarkers() {
		boolean valid = true;
		Map<Assessor, Integer> markerCount = new HashMap<>();
		for (MarkerUserDto markerDto : markerDtos) {
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
}
