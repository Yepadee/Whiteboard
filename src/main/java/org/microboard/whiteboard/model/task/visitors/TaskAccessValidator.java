package org.microboard.whiteboard.model.task.visitors;

import java.util.List;

import org.microboard.whiteboard.model.task.GroupTask;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.User;

public class TaskAccessValidator extends TaskVisitor {

	private boolean result;
	private User user;
	
	public TaskAccessValidator(User user) {
		this.user = user;
	}
	
	public boolean getResult() {
		return result;
	}
	
	@Override
	public void visit(SoloTask soloTask) {
		result = soloTask.getAccountable().getId() == user.getId();
	}

	@Override
	public void visit(GroupTask groupTask) {
		//TODO: Optimise using hql query.
		List<User> accountable = groupTask.getAccountable().getMembers();
		result = false;
		for (User groupMember : accountable) {
			if (groupMember.getId() == user.getId()) {
				result = true;
			}
		}
	}

}
