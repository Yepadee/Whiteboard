package org.microboard.whiteboard.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.user.visitors.UserVisitor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
@Table(name = "Users")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="perms", discriminatorType=DiscriminatorType.STRING)
public abstract class User {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String userName;
	private String password;
	
	@ManyToMany
    @JoinTable(
            name = "Users_Groups", 
            joinColumns = { @JoinColumn(name = "user_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "group_id") }
        )
	private List<Group> groups;
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy= "accountable")
	private List<SoloTask> tasks = new ArrayList<>();
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id; //comment
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String name) {
		this.userName = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public List<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	public List<SoloTask> getTasks() {
		return tasks;
	}
	
	public void setTasks(List<SoloTask> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(SoloTask task) {
		task.setAccountable(this);
		this.getTasks().add(task);
	}
	
	public abstract void accept(UserVisitor v);
}
