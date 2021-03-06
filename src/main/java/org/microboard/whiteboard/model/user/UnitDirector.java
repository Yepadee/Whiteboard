package org.microboard.whiteboard.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.user.visitors.UserVisitor;

@DiscriminatorValue("unit director")
@Entity
public class UnitDirector extends Assessor {
	public UnitDirector() {}
	
	public UnitDirector(User user) {
		super(user);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1935294899489506872L;
	@OneToMany(mappedBy= "creator")
	private List<Project> myProjects = new ArrayList<>();
	
	@ManyToMany(mappedBy= "helpers")
	private List<Project> assignedProjects = new ArrayList<>();
	
	public List<Project> getMyProjects() {
		return myProjects;
	}
	
	public void setMyProjects(List<Project> myProjects) {
		this.myProjects = myProjects;
	}
	
	public List<Project> getAssignedProjects() {
		return assignedProjects;
	}

	public void setAssignedProjects(List<Project> assignedProjects) {
		this.assignedProjects = assignedProjects;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
