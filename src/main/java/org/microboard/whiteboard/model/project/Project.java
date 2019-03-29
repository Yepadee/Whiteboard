package org.microboard.whiteboard.model.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;

import org.microboard.whiteboard.model.log.ProjectAction;
import org.microboard.whiteboard.model.log.TaskAction;
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
	private Long id;
	private String name;
	private String description;
	
	@ManyToOne(cascade = {CascadeType.REFRESH})
	private UnitDirector creator;
	
	@ManyToMany
	private List<UnitDirector> helpers = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="unit_id", nullable=false)
	private Unit unit;
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "project")
	private List<ProjectAction> actions = new ArrayList<>();
	
	
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

	public void addHelper(UnitDirector unitDirector) {
		helpers.add(unitDirector);
		unitDirector.getAssignedProjects().add(this);
	}
	
	public List<ProjectAction> getActions() {
		return actions;
	}
	public void setActions(List<ProjectAction> actions) {
		this.actions = actions;
	}
	
	public void addAction(ProjectAction action) {
		action.setProject(this);
		actions.add(action);
	}
	public abstract void accept(ProjectVisitor v);

}
