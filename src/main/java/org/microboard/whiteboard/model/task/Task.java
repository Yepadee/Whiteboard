package org.microboard.whiteboard.model.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

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
	
	@ElementCollection
	@CollectionTable(name="fileNames", joinColumns=@JoinColumn(name="task_id"))
	@Column(name="fileName")
	private List<String> fileNames= new ArrayList<>();
	
	private String status = "new";
	
	
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
	public List<String> getFileNames() {
		return fileNames;
	}
	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
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
	
	public void addFile(String fileName) {
		this.fileNames.add(fileName);
	}
	
	public void addMarker(Assessor assessor) {
		this.getMarkers().add(assessor);
		assessor.getToMark().add(this);
	}
	
	public List<String> getAllUploads() {
		List<String> allUploads = new ArrayList<String>();
		allUploads.addAll(fileNames);
		return allUploads;
	}
	public void removeFile(String filePath)
	{
		fileNames.remove(filePath);

	}
	
	public abstract void accept(TaskVisitor v);
}
