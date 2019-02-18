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

import org.microboard.whiteboard.model.user.Assessor;

@Entity
public class Feedback {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
	private long id;
	@ManyToOne
	private Assessor marker;
	private String txtFeedback;
	private boolean visable;
	
	@ElementCollection
	@CollectionTable(name="feedback_file_names", joinColumns=@JoinColumn(name="marker_task_id"))
	@Column(name="fileName")
	private List<String> fileNames = new ArrayList<>();
	
	
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
	public boolean isVisable() {
		return visable;
	}
	public void setVisable(boolean visable) {
		this.visable = visable;
	}
}
