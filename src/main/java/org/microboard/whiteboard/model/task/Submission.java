package org.microboard.whiteboard.model.task;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;

public class Submission {
private String txtSubmission;
	private long id;
	
	@ElementCollection
	@CollectionTable(name="fileNames", joinColumns=@JoinColumn(name="task_id"))
	@Column(name="fileName")
	private List<String> fileNames= new ArrayList<>();
	
	private List<Feedback> feedback;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public void addFile(String fileName) {
		this.fileNames.add(fileName);
	}
	public List<Feedback> getFeedback() {
		return feedback;
	}
	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}
}