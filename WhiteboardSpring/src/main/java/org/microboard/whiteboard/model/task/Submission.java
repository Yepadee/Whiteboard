package org.microboard.whiteboard.model.task;

import java.io.File;
import java.util.List;

import javax.persistence.Embeddable;

@Embeddable
public class Submission {
	private List<File> files;
	private String comments;
}
