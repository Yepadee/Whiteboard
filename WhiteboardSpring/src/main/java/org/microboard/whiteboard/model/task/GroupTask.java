package org.microboard.whiteboard.model.task;

import javax.persistence.ManyToOne;

import org.microboard.whiteboard.model.user.Group;

public class GroupTask extends Task{
	@ManyToOne
	private Group accountable;
	
	/** TODO:
	 * add feedback
	 */
}
