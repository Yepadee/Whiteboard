package org.microboard.whiteboard.model.assessment;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.SoloTask;

@Entity
@DiscriminatorValue("solo")
@SequenceGenerator(name = "default_gen", sequenceName = "role_seq", allocationSize = 1)
public class SoloAssessment extends Assessment{
	@ManyToOne
	private SoloProject project;
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "soloAssessment")
	private List<SoloTask> tasks = new ArrayList<>();

	public SoloProject getProject() {
		return project;
	}

	public void setProject(SoloProject project) {
		this.project = project;
	}

	public List<SoloTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<SoloTask> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(SoloTask task) {
		task.setSoloAssessment(this);
		this.tasks.add(task);
	}
}
