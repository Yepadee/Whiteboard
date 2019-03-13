package org.microboard.whiteboard.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.user.visitors.UserVisitor;

@DiscriminatorValue("assessor")
@Entity
public class Assessor extends User {
	private static final long serialVersionUID = 2844404820575452793L;

	public Assessor() {}
	
	public Assessor(User user) {
		super(user);
	}
	
	@OneToMany(mappedBy="marker")
	private List<Feedback> taskFeedback = new ArrayList<>();
	
	public List<Feedback> getTaskFeedback() {
		return taskFeedback;
	}

	public void setTaskFeedback(List<Feedback> taskFeedback) {
		this.taskFeedback = taskFeedback;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void addTaskFeedback(Feedback feedback) {
		feedback.setMarker(this);
		taskFeedback.add(feedback);
	}
	
	public void removeTaskFeedack(Feedback feedback) {
		taskFeedback.remove(feedback);
	}
	
	@Override
	public void accept(UserVisitor v) {
		v.visit(this);
	}
	
}
