package org.microboard.whiteboard.dto.user;

import org.microboard.whiteboard.model.user.Assessor;

public abstract class MarkerDto {
	private Assessor marker;
	
	public Assessor getMarker() {
		return marker;
	}
	public void setMarker(Assessor marker) {
		this.marker = marker;
	}
	
}
