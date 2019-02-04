package org.microboard.whiteboard.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.user.visitors.UserVisitor;

@DiscriminatorValue("assessor")
@Entity
public class Assessor extends User {

	@ManyToMany(mappedBy="markers")
	private List<Task> toMark = new ArrayList<>();
	
	public List<Task> getToMark() {
		return toMark;
	}

	public void setToMark(List<Task> toMark) {
		this.toMark = toMark;
	}

	public void addTaskToMark(Task task) {
		toMark.add(task);
		task.getMarkers().add(this);
	}
	
	public void removeTaskToMark(Task task) {
		task.getMarkers().remove(this);
		toMark.remove(task);
	}
	
	@Override
	public void accept(UserVisitor v) {
		v.visit(this);
	}
	
}
