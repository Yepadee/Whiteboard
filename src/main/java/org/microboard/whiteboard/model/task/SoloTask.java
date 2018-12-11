package org.microboard.whiteboard.model.task;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.user.User;

@Entity
@DiscriminatorValue("solo")
public class SoloTask extends Task{
	@OneToOne
	private User accountable;
	
	@ManyToOne
	@JoinColumn(name="assessment_id", nullable=false)
	private SoloAssessment assessment;

	public User getAccountable() {
		return accountable;
	}

	public void setAccountable(User accountable) {
		this.accountable = accountable;
	}

	public SoloAssessment getAssessment() {
		return assessment;
	}

	public void setAssessment(SoloAssessment assessment) {
		this.assessment = assessment;
	}
	
	/** TODO:
	 * add feedback
	 */
}
