package org.microboard.whiteboard.model.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.microboard.whiteboard.model.assessment.GroupAssessment;
import org.microboard.whiteboard.model.project.visitors.ProjectVisitor;
import org.microboard.whiteboard.model.user.Group;

@Entity
@DiscriminatorValue("group")
public class GroupProject extends Project {
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "project")
	private List<GroupAssessment> assessments = new ArrayList<>();
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "project")
	private List<Group> groups = new ArrayList<>();

	
	public List<GroupAssessment> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<GroupAssessment> assessments) {
		this.assessments = assessments;
	}
	
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public void addAssessment(GroupAssessment assessment) {
		assessment.setProject(this);
		this.assessments.add(assessment);
	}
	
	public void addGroup(Group group) {
		group.setProject(this);
		this.groups.add(group);
	}

	@Override
	public void accept(ProjectVisitor v) {
		v.visit(this);
	}
}
