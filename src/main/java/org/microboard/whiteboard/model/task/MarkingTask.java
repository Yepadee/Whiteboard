package org.microboard.whiteboard.model.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MarkingTask {
	
	private long id;
	private String txtSubmission;
	private String status = "new";
	private List<File> uploadedFiles =  new ArrayList<>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public List<File> getUploadedFiles() {
		return uploadedFiles;
	}
	public void setUploadedFiles(List<File> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	
	
}
