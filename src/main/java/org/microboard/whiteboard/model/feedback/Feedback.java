package org.microboard.whiteboard.model.feedback;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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

import org.microboard.whiteboard.dto.task.FileDto;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.user.Assessor;

@Entity
public class Feedback {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
	private Long id;
	
	@ManyToOne
	private Task task;
	
	@ManyToOne
	private Assessor marker;
	
	private Date markerExtension;
	
	private String txtFeedback;
	private String status = "new";
	private boolean visible;
	private Integer marks;
	@ElementCollection
	@CollectionTable(name="feedback_file_names", joinColumns=@JoinColumn(name="marker_task_id"))
	@Column(name="fileName")
	private List<String> fileNames = new ArrayList<>();
	
	Feedback() {}
	
	public Feedback(Task task) {
		setTask(task);
	}
	
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
	public void deleteFile(String filePath) {
		fileNames.remove(filePath);
	}
	public void addFile(String fileName) {
		this.fileNames.add(fileName);
	}
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visable) {
		this.visible = visable;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean notEmpty() {
		return marks != null;
	}
	
	public Integer getMarks() {
		return marks;
	}
	public void setMarks(Integer marks) {
		this.marks = marks;
	}
	
	public List<FileDto> getFileInfo() {
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

	public Date getMarkerExtension() {
		return markerExtension;
	}

	public void setMarkerExtension(Date markerExtension) {
		this.markerExtension = markerExtension;
	}
}
