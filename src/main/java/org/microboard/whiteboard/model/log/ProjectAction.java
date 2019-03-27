package org.microboard.whiteboard.model.log;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.user.User;

@Entity
@DiscriminatorValue("project")
@SequenceGenerator(name = "default_gen", sequenceName = "role_seq", allocationSize = 1)
public class ProjectAction extends Action {
	@ManyToOne
	private Project project;
	
	ProjectAction() {}

	public ProjectAction(User user, String description) {
		super(user, description);
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
}
