package org.microboard.whiteboard.model.other;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.user.User;

//@Entity
public class Log {
	//@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String action;
	
	//@OneToMany
	private User user;
	
	//@OneToMany
	private Project project;
	
	private Date date;
	
	public Log(Project project, String action) {
		this.project = project;
		this.action = action;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
