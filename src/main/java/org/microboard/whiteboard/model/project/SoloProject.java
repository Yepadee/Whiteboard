package org.microboard.whiteboard.model.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.user.User;

@Entity
@DiscriminatorValue("solo")
public class SoloProject extends Project {
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy= "project")
	private List<SoloAssessment> assessments = new ArrayList<>();
	
	@ManyToMany
	private List<User> cohort = new ArrayList<>();
	

	public List<User> getCohort() {
		return cohort;
	}

	public void setCohort(List<User> cohort) {
		this.cohort = cohort;
	}

	public List<SoloAssessment> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<SoloAssessment> assessments) {
		this.assessments = assessments;
	}
	
	public void addAssessment(SoloAssessment assessment) {
		assessment.setProject(this);
		this.assessments.add(assessment);
	}
	
}
