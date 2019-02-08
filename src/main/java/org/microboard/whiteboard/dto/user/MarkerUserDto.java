package org.microboard.whiteboard.dto.user;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.model.user.User;

public class MarkerUserDto extends MarkerDto {
	private List<User> toMark = new ArrayList<>();
	
	public List<User> getToMark() {
		return toMark;
	}
	public void setToMark(List<User> toMark) {
		this.toMark = toMark;
	}

}
