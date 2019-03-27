package org.microboard.whiteboard.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Version;

import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.user.visitors.UserVisitor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="perms", discriminatorType=DiscriminatorType.STRING)
@NamedNativeQuery(name="User.changePerms", query="UPDATE USER SET PERMS = ?, " + "VERSION = VERSION + 1 WHERE ID = ?")
public abstract class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6187306015251367488L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String userName;
	private String password;
	
	@Version private int version;
	
	@ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
	private List<Group> groups = new ArrayList<>();
	
	@OneToMany(mappedBy = "accountable", fetch = FetchType.LAZY)
	private List<SoloTask> soloTasks = new ArrayList<>();
	
	@ManyToMany(mappedBy = "cohort", fetch = FetchType.LAZY)
	private List<Unit> units = new ArrayList<>();
	
	public User() {}
	
	public User(User user) {
		setId(user.getId());
		setUserName(user.getUserName());
		setPassword(user.getPassword());
		setGroups(user.getGroups());
		setSoloTasks(user.getSoloTasks());
		setUnits(user.getUnits());
	}
	
	public Long getId() {
		return id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getName() {
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
	
	public String toString() {
		return getUserName();
	}
	
}
