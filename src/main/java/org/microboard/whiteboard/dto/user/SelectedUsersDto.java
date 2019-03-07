package org.microboard.whiteboard.dto.user;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.model.user.User;

public class SelectedUsersDto {
	
	private List<User> selectedUsers = new ArrayList<>();

	public List<User> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}
	
	
	
}
