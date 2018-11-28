package org.microboard.whiteboard.model.project;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.microboard.whiteboard.model.user.UnitDirector;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.DiscriminatorType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class Project {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	private String name;
	private String description;


	@OneToOne
	private UnitDirector creator;
	@OneToMany
	private List<UnitDirector> helpers;
	
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
	
}
