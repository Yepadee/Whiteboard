package org.microboard.whiteboard.dto.user;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.model.user.User;

public class SelectedUsersDto {
	
	private String newPerms;
	private List<User> selectedStudents = new ArrayList<>();
	private List<User> selectedAssessors = new ArrayList<>();
	private List<User> selectedUnitDirectors = new ArrayList<>();
	
	public String getNewPerms() {
		return newPerms;
	}
	public void setNewPerms(String newPerms) {
		this.newPerms = newPerms;
	}
	public List<User> getSelectedStudents() {
		return selectedStudents;
	}
	public void setSelectedStudents(List<User> selectedStudents) {
		this.selectedStudents = selectedStudents;
	}
	public List<User> getSelectedAssessors() {
		return selectedAssessors;
	}
	public void setSelectedAssessors(List<User> selectedAssessors) {
		this.selectedAssessors = selectedAssessors;
	}
	public List<User> getSelectedUnitDirectors() {
		return selectedUnitDirectors;
	}
	public void setSelectedUnitDirectors(List<User> selectedUnitDirectors) {
		this.selectedUnitDirectors = selectedUnitDirectors;
	}

	public List<User> getSelectedUsers() {
		List<User> selected = new ArrayList<>();
		selected.addAll(selectedStudents);
		selected.addAll(selectedAssessors);
		selected.addAll(selectedUnitDirectors);

		return selected;
	}
}
