package org.microboard.whiteboard.model.task;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.microboard.whiteboard.model.assessment.GroupAssessment;
import org.microboard.whiteboard.model.user.Group;

@Entity
@DiscriminatorValue("group")
public class GroupTask extends Task {
	@ManyToOne
	private Group accountable;
	
	@ManyToOne
	@JoinColumn(name="assessment_id", nullable=false)
	private GroupAssessment assessment;

	public Group getAccountable() {
		return accountable;
	}

	public void setAccountable(Group accountable) {
		this.accountable = accountable;
	}

	public GroupAssessment getAssessment() {
		return assessment;
	}

	public void setAssessment(GroupAssessment assessment) {
		this.assessment = assessment;
	}
	
	/** TODO:
	 * add feedback
	 */
	
}
