package org.microboard.whiteboard.model.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.microboard.whiteboard.model.assessment.GroupAssessment;

@Entity
@DiscriminatorValue("solo")
public class SoloProject extends Project {
	@OneToMany
	private List<GroupAssessment> assessments = new ArrayList<>();

	public List<GroupAssessment> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<GroupAssessment> assessments) {
		this.assessments = assessments;
	}
}
