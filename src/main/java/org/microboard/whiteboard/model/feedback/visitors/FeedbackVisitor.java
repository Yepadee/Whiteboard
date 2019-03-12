package org.microboard.whiteboard.model.feedback.visitors;

import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.feedback.GroupMemberFeedback;

public abstract class FeedbackVisitor {
	public abstract void visit(Feedback feedback);
	public abstract void visit(GroupMemberFeedback groupMemberFeedback);
}
