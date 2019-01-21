package org.microboard.whiteboard.model.assessment;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.task.GroupTask;

@Entity
@DiscriminatorValue("group")
@SequenceGenerator(name = "default_gen", sequenceName = "role_seq", allocationSize = 1)
public class GroupAssessment extends Assessment {
	@ManyToOne//(cascade = {CascadeType.ALL})
	private GroupProject project;
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy= "groupAssessment")
	private List<GroupTask> tasks = new ArrayList<>();

	public GroupProject getProject() {
		return project;
	}

	public void setProject(GroupProject project) {
		this.project = project;
	}

	public List<GroupTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<GroupTask> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(GroupTask task) {
		task.setGroupAssessment(this);
		this.tasks.add(task);
	}
	
}
