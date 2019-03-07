package org.microboard.whiteboard.model.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.microboard.whiteboard.model.assessment.Assessment;
import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.task.visitors.TaskVisitor;
import org.microboard.whiteboard.model.user.Assessor;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class Task {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
	private Long id;
	
	private int studentExtension;
	private int markerExtension;
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinTable(name="assessor_feedback", joinColumns=@JoinColumn(name="assessor_id"))
	@MapKeyColumn(name="assessor_id")
	private Map<Assessor, Feedback> feedback = new HashMap<>();
	
	private String txtSubmission;
	
	@ElementCollection
	@CollectionTable(name="fileNames", joinColumns=@JoinColumn(name="task_id"))
	@Column(name="fileName")
	private List<String> fileNames= new ArrayList<>();
	
	private String status = "new";
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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

	public Map<Assessor, Feedback> getFeedback() {
		return feedback;
	}
	public void setFeedback(Map<Assessor, Feedback> feedback) {
		this.feedback = feedback;
	}
	public void addFile(String fileName) {
		this.fileNames.add(fileName);
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
	
	public Feedback getIndividualFeedback(Assessor assessor) {
		return feedback.get(assessor);
	}
	
	public void addMarker(Assessor assessor) {
		Feedback feedbackTask = new Feedback();
		feedbackTask.setTask(this);
		assessor.addTaskFeedback(feedbackTask);
		feedback.put(assessor, feedbackTask);
	}
	
	public void removeMarker(Assessor assessor) {
		feedback.remove(assessor);
		assessor.removeTaskFeedack(feedback.get(assessor));		
	}
	
	public List<Assessor> getMarkers() {
		return new ArrayList<>(feedback.keySet());
	}
	
	public abstract Assessment getAssessment();
	
	public abstract void accept(TaskVisitor v);
}
