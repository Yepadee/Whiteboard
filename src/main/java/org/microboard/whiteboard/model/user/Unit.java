package org.microboard.whiteboard.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Unit {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String unitName;
	private String unitCode;
	
	@ManyToMany()
	private List<User> cohort = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public List<User> getCohort() {
		return cohort;
	}

	public void setCohort(List<User> cohort) {
		this.cohort = cohort;
	}

	public void addUser(User user) {
		user.getUnits().add(this);
		cohort.add(user);
	}
	
	public void removeUser(User user) {
		user.getUnits().remove(this);
		cohort.remove(user);	
	}
	
}
