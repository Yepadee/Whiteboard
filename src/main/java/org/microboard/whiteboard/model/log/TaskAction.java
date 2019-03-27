package org.microboard.whiteboard.model.log;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.user.User;

@Entity
@DiscriminatorValue("task")
@SequenceGenerator(name = "default_gen", sequenceName = "role_seq", allocationSize = 1)
public class TaskAction extends Action {
	@ManyToOne
	private Task task;
	
	TaskAction() {}

	public TaskAction(User user, String description) {
		super(user, description);
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
