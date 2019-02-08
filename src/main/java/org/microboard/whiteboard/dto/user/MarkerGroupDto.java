package org.microboard.whiteboard.dto.user;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.model.user.Group;

public class MarkerGroupDto extends MarkerDto {
	private List<Group> toMark = new ArrayList<>();
	
	public List<Group> getToMark() {
		return toMark;
	}
	public void setToMark(List<Group> toMark) {
		this.toMark = toMark;
	}
}
