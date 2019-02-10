package org.microboard.whiteboard.model.project;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.microboard.whiteboard.model.project.visitors.ProjectVisitor;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.model.user.UnitDirector;

import javax.persistence.DiscriminatorType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class Project {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String name;
	private String description;
	
	@ManyToOne
	private UnitDirector creator;
	@ManyToMany
	private List<UnitDirector> helpers;
	
	@ManyToOne
	@JoinColumn(name="unit_id", nullable=false)
	private Unit unit;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public UnitDirector getCreator() {
		return creator;
	}
	public void setCreator(UnitDirector creator) {
		this.creator = creator;
	}
	public List<UnitDirector> getHelpers() {
		return helpers;
	}
	public void setHelpers(List<UnitDirector> helpers) {
		this.helpers = helpers;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public abstract void accept(ProjectVisitor v);
}
