package org.microboard.whiteboard.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.task.GroupTask;

@Entity
@Table(name="ProjectGroup") //"Group" is a reserved keyword.
public class Group {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
	private Long id;
	private String name;
	
	@ManyToMany
    @JoinTable(
            name = "Groups_Members", 
            joinColumns = { @JoinColumn(name = "group_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private List<User> members = new ArrayList<>();
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy= "accountable")
	private List<GroupTask> tasks = new ArrayList<>();
	
	@ManyToOne
	private GroupProject project;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User> getMembers() {
		return members;
	}
	public void setMembers(List<User> members) {
		this.members = members;
	}
	public List<GroupTask> getTasks() {
		return tasks;
	}
	public void setTasks(List<GroupTask> tasks) {
		this.tasks = tasks;
	}
	public GroupProject getProject() {
		return project;
	}
	public void setProject(GroupProject project) {
		this.project = project;
	}
	
	public void addTask(GroupTask task) {
		task.setAccountable(this);
		this.tasks.add(task);
	}
	
	public void addMember(User user) {
		members.add(user);
		user.getGroups().add(this);
	}
}
