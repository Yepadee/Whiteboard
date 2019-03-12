package org.microboard.whiteboard.model.feedback;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.microboard.whiteboard.model.feedback.visitors.FeedbackVisitor;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskVisitor;
import org.microboard.whiteboard.model.user.Assessor;

@Entity
public class Feedback {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
	private Long id;
	
	@ManyToOne
	private Task task;
	
	@ManyToOne
	private Assessor marker;
	
	private String txtFeedback;
	private String status;
	private boolean visable;
	
	@ElementCollection
	@CollectionTable(name="feedback_file_names", joinColumns=@JoinColumn(name="marker_task_id"))
	@Column(name="fileName")
	private List<String> fileNames = new ArrayList<>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public Assessor getMarker() {
		return marker;
	}
	public void setMarker(Assessor marker) {
		this.marker = marker;
	}
	public String getTxtFeedback() {
		return txtFeedback;
	}
	public void setTxtFeedback(String txtFeedback) {
		this.txtFeedback = txtFeedback;
	}
	public List<String> getFileNames() {
		return fileNames;
	}
	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}
	public void addFile(String fileName) {
		this.fileNames.add(fileName);
	}
	public boolean isVisable() {
		return visable;
	}
	public void setVisable(boolean visable) {
		this.visable = visable;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void accept(FeedbackVisitor v) {
		v.visit(this);
	}
}
