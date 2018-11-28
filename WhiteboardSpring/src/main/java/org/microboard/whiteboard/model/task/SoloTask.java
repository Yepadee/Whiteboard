package org.microboard.whiteboard.model.task;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.user.User;

@Entity
public class SoloTask extends Task{
	@ManyToOne
	private User accountable;
	
	@ManyToOne
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
