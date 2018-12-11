package org.microboard.whiteboard.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.user.visitors.UserVisitor;

@DiscriminatorValue("unit director")
@Entity
public class UnitDirector extends User {
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy= "creator")
	private List<Project> myProjects = new ArrayList<>();
	
	public List<Project> getMyProjects() {
		return myProjects;
	}
	
	public void setMyProjects(List<Project> myProjects) {
		this.myProjects = myProjects;
	}
	
	public void addProject(Project project) {
		project.setCreator(this);
		myProjects.add(project);
	}
	
	@Override
	public void accept(UserVisitor v) {
		v.visit(this);
	}
	
}
