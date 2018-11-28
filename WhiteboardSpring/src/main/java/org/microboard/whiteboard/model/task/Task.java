package org.microboard.whiteboard.model.task;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

import org.microboard.whiteboard.model.user.User;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class Task {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private int studentExtension;
	private int markerExtension;
	
	@ManyToMany
	private List<User> markers;
	
	//@Embedded
	//private Submission submission;
	private String status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getStudentExtension() {
		return studentExtension;
	}
	public void setStudentExtension(int studentExtension) {
		this.studentExtension = studentExtension;
	}
	public int getMarkerExtension() {
		return markerExtension;
	}
	public void setMarkerExtension(int markerExtension) {
		this.markerExtension = markerExtension;
	}
	public List<User> getMarkers() {
		return markers;
	}
	public void setMarkers(List<User> markers) {
		this.markers = markers;
	}
	/*public Submission getSubmission() {
		return submission;
	}
	public void setSubmission(Submission submission) {
		this.submission = submission;
	}*/
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
