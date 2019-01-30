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
	
	@ManyToMany(mappedBy = "members")
	private List<Group> groups = new ArrayList<>();
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy= "accountable")
	private List<SoloTask> soloTasks = new ArrayList<>();
	
	@ManyToMany(mappedBy = "cohort")
	private List<Unit> units = new ArrayList<>();
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public List<SoloTask> getSoloTasks() {
		return soloTasks;
	}
	
	public void setSoloTasks(List<SoloTask> soloTasks) {
		this.soloTasks = soloTasks;
	}
	
	public void addTask(SoloTask task) {
		task.setAccountable(this);
		this.getSoloTasks().add(task);
	}
	
	public List<Task> getAllTasks() {
		List<Task> allTasks = new ArrayList<Task>();
		allTasks.addAll(soloTasks);
		for (Group g : groups) {
			allTasks.addAll(g.getTasks());
		}
		return allTasks;
	}
	
	public abstract void accept(UserVisitor v);

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}
	
	public void addUnit(Unit unit) {
		units.add(unit);
		unit.getCohort().add(this);
	}
	
}
