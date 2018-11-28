package org.microboard.whiteboard.model.task;

import javax.persistence.ManyToOne;

import org.microboard.whiteboard.model.user.User;

public class SoloTask extends Task{
	@ManyToOne
	private User accountable;
	
	/** TODO:
	 * add feedback
	 */
}
