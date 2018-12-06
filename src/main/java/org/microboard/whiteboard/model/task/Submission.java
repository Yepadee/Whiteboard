package org.microboard.whiteboard.model.task;

import java.io.File;
import java.util.List;

import javax.persistence.Embeddable;

@Embeddable
public class Submission {
	private List<File> files;
	private String comments;
	
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
