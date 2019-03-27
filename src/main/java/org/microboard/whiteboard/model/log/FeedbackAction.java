package org.microboard.whiteboard.model.log;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.user.User;

@Entity
@DiscriminatorValue("feedback")
@SequenceGenerator(name = "default_gen", sequenceName = "role_seq", allocationSize = 1)
public class FeedbackAction extends Action {
	@ManyToOne
	private Feedback feedback;

	FeedbackAction() {}

	public FeedbackAction(User user, String description) {
		super(user, description);
	}
	
	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}
}
