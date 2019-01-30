package org.microboard.whiteboard.model.project.builder;

import java.util.List;

public abstract class ProjectTemplate {
	private String name;
	private String description;
	private List<Integer> helperIds;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Integer> getHelperIds() {
		return helperIds;
	}
	public void setHelperIds(List<Integer> helperIds) {
		this.helperIds = helperIds;
	}

}
