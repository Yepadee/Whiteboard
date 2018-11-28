package org.microboard.whiteboard.model.user;

import java.util.List;

import javax.persistence.*;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.task.GroupTask;

@Entity
public class Group {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String name;
	
	@OneToMany
	private List<User> members;
	
	@OneToMany
	private List<GroupTask> tasks;
	@ManyToOne
	private Project project;
}
