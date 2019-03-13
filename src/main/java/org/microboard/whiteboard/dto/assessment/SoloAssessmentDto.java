package org.microboard.whiteboard.dto.assessment;

import java.util.ArrayList;
import java.util.List;
import org.microboard.whiteboard.dto.user.MarkerDto;
import org.microboard.whiteboard.dto.user.MarkerUserDto;

public class SoloAssessmentDto extends AssessmentDto {
	private List<MarkerUserDto> soloMarkerDtos = new ArrayList<>();
	
	public List<MarkerUserDto> getSoloMarkerDtos() {
		return soloMarkerDtos;
	}

	public void setSoloMarkerDtos(List<MarkerUserDto> soloMarkerDtos) {
		this.soloMarkerDtos = soloMarkerDtos;
	}

	@Override
	protected List<MarkerDto> getMarkerDtos() {
		List<MarkerDto> markerDtos = new ArrayList<>();
		markerDtos.addAll(soloMarkerDtos);
		return markerDtos;
	}
}
