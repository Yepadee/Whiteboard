package org.microboard.whiteboard.model.task;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;

import org.microboard.whiteboard.model.user.Assessor;

public class Feedback {
	private long id;
	private Assessor marker;
	private Submission toMark;
	private String txtFeedback;
	
	@ElementCollection
	@CollectionTable(name="fileNames", joinColumns=@JoinColumn(name="marker_task_id"))
	@Column(name="fileName")
	private List<String> fileNames= new ArrayList<>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Assessor getMarker() {
		return marker;
	}
	public void setMarker(Assessor marker) {
		this.marker = marker;
	}
	public Submission getToMark() {
		return toMark;
	}
	public void setToMark(Submission toMark) {
		this.toMark = toMark;
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
}
