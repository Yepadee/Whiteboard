package org.microboard.whiteboard.model.assessment;

import java.util.Date;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class Assessment {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assessment_gen")
	private Long id;
	
	private String name;
	private String description;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
	private Date studentDeadline;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
	private Date markerDeadline;
	
	private int weight;

	private Boolean marksReleased;
	
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

	public Date getStudentDeadline() {
		return studentDeadline;
	}

	public void setStudentDeadline(Date studentDeadline) {
		this.studentDeadline = studentDeadline;
	}

	public Date getMarkerDeadline() {
		return markerDeadline;
	}

	public void setMarkerDeadline(Date markerDeadline) {
		this.markerDeadline = markerDeadline;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Boolean getMarksReleased() {
		return marksReleased;
	}

	public void setMarksReleased(Boolean marksReleased) {
		this.marksReleased = marksReleased;
	}
	
	
}
