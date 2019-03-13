package org.microboard.whiteboard.dto.assessment;

import java.util.ArrayList;
import java.util.List;
import org.microboard.whiteboard.dto.user.MarkerDto;
import org.microboard.whiteboard.dto.user.MarkerGroupDto;

public class GroupAssessmentDto extends AssessmentDto {
	private List<MarkerGroupDto> groupMarkerDtos = new ArrayList<>();
	
	

	public List<MarkerGroupDto> getGroupMarkerDtos() {
		return groupMarkerDtos;
	}



	public void setGroupMarkerDtos(List<MarkerGroupDto> groupMarkerDtos) {
		this.groupMarkerDtos = groupMarkerDtos;
	}



	@Override
	protected List<MarkerDto> getMarkerDtos() {
		List<MarkerDto> markerDtos = new ArrayList<>();
		markerDtos.addAll(groupMarkerDtos);
		return markerDtos;
	}

}
