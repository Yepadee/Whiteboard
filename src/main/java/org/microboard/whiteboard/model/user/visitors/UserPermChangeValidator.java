package org.microboard.whiteboard.model.user.visitors;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;

public class UserPermChangeValidator extends UserVisitor {
	private boolean valid;
	private String newPerms;
	private String errorMsg = "";
	
	public UserPermChangeValidator(String newPerms) {
		this.newPerms = newPerms;
	}
	
	public boolean getResult() {
		return valid;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	@Override
	public void visit(Student student) {
		if (newPerms.equals("student")) {
			valid = false;
			errorMsg +=  "\"" + student.getUserName() + "\"" + " is already a student.\n";
		} else if (newPerms.equals("assessor")) {
			valid = true;
		} else if (newPerms.equals("unit_director")) {
			valid = true;
		}
	}

	@Override
	public void visit(Assessor assessor) {
		if (newPerms.equals("student")) {
			valid = assessor.getTaskFeedback().isEmpty();
			if (! valid) {
				errorMsg += "\"" + assessor.getUserName() + "\"" + " cannot be demoted from assessor since they have feedback associated with them.\n";
			}
		} else if (newPerms.equals("assessor")) {
			valid = false;
			errorMsg += "\"" + assessor.getUserName() + "\"" + " is already an assessor.\n";
		} else if (newPerms.equals("unit_director")) {
			valid = true;
		}
	}

	@Override
	public void visit(UnitDirector unitDirector) {
		if (newPerms.equals("student") || newPerms.equals("assessor")) {
			valid = unitDirector.getMyProjects().isEmpty();
			if (! valid) {
				errorMsg += "\"" + unitDirector.getUserName() + "\"" + " cannot be demoted from unit director since they have projects associated with them.\n";
			}
		} else if (newPerms.equals("unit_director")) {
			valid = false;
			errorMsg += "\"" + unitDirector.getUserName() + "\"" + " is already a unit director.\n";
		}
		
	}
}
