package org.microboard.whiteboard.model.task;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.microboard.whiteboard.model.assessment.Assessment;
import org.microboard.whiteboard.model.assessment.GroupAssessment;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.task.visitors.TaskVisitor;
import org.microboard.whiteboard.model.user.User;

@Entity
@DiscriminatorValue("solo")
@SequenceGenerator(name = "default_gen", sequenceName = "role_seq", allocationSize = 1)
public class SoloTask extends Task {
	@ManyToOne
	private User accountable;
	
	@ManyToOne
	@JoinColumn(name="assessment_id", nullable=false)
	private SoloAssessment soloAssessment;

	public User getAccountable() {
		return accountable;
	}

	public void setAccountable(User accountable) {
		this.accountable = accountable;
	}

	public SoloAssessment getSoloAssessment() {
		return soloAssessment;
	}

	public void setSoloAssessment(SoloAssessment assessment) {
		this.soloAssessment = assessment;
	}
	
	public Assessment getAssessment() {
		return soloAssessment;
	}

	@Override
	public void accept(TaskVisitor v) {
		v.visit(this);
	}

}
