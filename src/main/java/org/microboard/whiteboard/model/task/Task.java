package org.microboard.whiteboard.model.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.microboard.whiteboard.dto.task.FileDto;
import org.microboard.whiteboard.model.assessment.Assessment;
import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.log.TaskAction;
import org.microboard.whiteboard.model.task.visitors.TaskVisitor;
import org.microboard.whiteboard.model.user.Assessor;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class Task {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
	private Long id;
	
	private Date studentExtension;
	
	@OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Feedback reconciledFeedback = new Feedback(this);
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinTable(name="assessor_feedback", joinColumns=@JoinColumn(name="assessor_id"))
	@MapKeyColumn(name="assessor_id")
	private Map<Assessor, Feedback> feedback = new HashMap<>();
	
	private String txtSubmission;
	
	@ElementCollection
	@CollectionTable(name="fileNames", joinColumns=@JoinColumn(name="task_id"))
	@Column(name="fileName")
	private List<String> fileNames = new ArrayList<>();
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "task")
	private List<TaskAction> actions = new ArrayList<>();
	
	private String status = "new";
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Date getStudentExtension() {
		return studentExtension;
	}
	public void setStudentExtension(Date studentExtension) {
		this.studentExtension = studentExtension;
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
	public Feedback getReconciledFeedback() {
		return reconciledFeedback;
	}
	public void setReconciledFeedback(Feedback reconciledFeedback) {
		this.reconciledFeedback = reconciledFeedback;
	}
	public void addFile(String fileName) {
		this.fileNames.add(fileName);
	}
	public List<String> getAllUploads() {
		List<String> allUploads = new ArrayList<String>();
		allUploads.addAll(fileNames);
		return allUploads;
	}
	public void removeFile(String filePath) {
		fileNames.remove(filePath);

	}
	public Feedback getIndividualFeedback(Assessor assessor) {
		return feedback.get(assessor);
	}
	public void addMarker(Assessor assessor) {
		Feedback feedbackTask = new Feedback(this);
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
	public Integer getNumMarkers() {
		return feedback.size();
	}
	public Integer getFeedbackSubmitted() {
		int feedbackSubmitted = 0;
		for (Feedback feedback : feedback.values()) {
			if (feedback.notEmpty()) {
				feedbackSubmitted ++;
			}
		}
		return feedbackSubmitted;
	}
	public List<FileDto> getFileInfo() {
		//Look into making a mock file to test this
		List<FileDto> fileinfo = new ArrayList<>();
		for (String filepath : getFileNames()) {
			FileDto f = new FileDto();
			f.setFileName(filepath.substring(filepath.lastIndexOf("/")+1));
			File file = new File(filepath);
			f.setFileSize(Long.toString(file.length()/1024) + "KB");
			f.setFilePath(filepath);
			fileinfo.add(f);	
		}
		return fileinfo;
	}
	
	public List<TaskAction> getActions() {
		return actions;
	}
	public void setActions(List<TaskAction> actions) {
		this.actions = actions;
	}
	
	public void addAction(TaskAction action) {
		action.setTask(this);
		actions.add(action);
	}
	
	public abstract Assessment getAssessment();
	
	public abstract void accept(TaskVisitor v);
}
