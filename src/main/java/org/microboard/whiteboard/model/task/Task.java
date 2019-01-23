package org.microboard.whiteboard.model.task;

import java.util.ArrayList;
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
import org.microboard.whiteboard.model.task.visitors.TaskVisitor;
import org.microboard.whiteboard.model.user.Assessor;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class Task {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
	private long id;
	
	private int studentExtension;
	private int markerExtension;
	
	@ManyToMany
	private List<Assessor> markers = new ArrayList<>();
	
	private String txtSubmission;
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
	public List<Assessor> getMarkers() {
		return markers;
	}
	public void setMarkers(List<Assessor> markers) {
		this.markers = markers;
	}
	public String getTxtSubmission() {
		return txtSubmission;
	}
	public void setTxtSubmission(String txtSubmission) {
		this.txtSubmission = txtSubmission;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void addMarker(Assessor assessor) {
		this.markers.add(assessor);
		assessor.addTaskToMark(this);
	}
	
	public abstract void accept(TaskVisitor v);
}
