package org.microboard.whiteboard.model.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.visitors.ProjectVisitor;

@Entity
@DiscriminatorValue("solo")
public class SoloProject extends Project {
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy= "project")
	private List<SoloAssessment> assessments = new ArrayList<>();

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
	
	@Override
	public void accept(ProjectVisitor v) {
		v.visit(this);
	}
}
