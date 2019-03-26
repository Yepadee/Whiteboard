package org.microboard.whiteboard.model.task;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.microboard.whiteboard.model.assessment.Assessment;
import org.microboard.whiteboard.model.assessment.GroupAssessment;
import org.microboard.whiteboard.model.feedback.GroupMemberFeedback;
import org.microboard.whiteboard.model.task.visitors.TaskVisitor;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Group;
import org.microboard.whiteboard.model.user.User;

@Entity
@DiscriminatorValue("group")
@SequenceGenerator(name = "default_gen", sequenceName = "role_seq", allocationSize = 1)
public class GroupTask extends Task {
	@ManyToOne
	private Group accountable;
	
	@ManyToOne
	@JoinColumn(name="assessment_id", nullable=false)
	private GroupAssessment groupAssessment;
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinTable(name="group_task_member_feedback", joinColumns=@JoinColumn(name="group_task_id"))
	@MapKeyColumn(name="user_id")
	private Map<User, GroupMemberFeedback> groupMemberFeedback = new HashMap<>();
	
	public Group getAccountable() {
		return accountable;
	}

	public void setAccountable(Group accountable) {
		this.accountable = accountable;
	}
	
	public GroupAssessment getGroupAssessment() {
		return groupAssessment;
	}

	public void setGroupAssessment(GroupAssessment groupAssessment) {
		this.groupAssessment = groupAssessment;
	}
	
	public Assessment getAssessment() {
		return groupAssessment;
	}
	
	public Map<User, GroupMemberFeedback> getGroupMemberFeedback() {
		return groupMemberFeedback;
	}

	public void setGroupMemberFeedback(Map<User, GroupMemberFeedback> groupMemberFeedback) {
		this.groupMemberFeedback = groupMemberFeedback;
	}
	
	public void addIndividualFeedback(User user) {
		GroupMemberFeedback feedback = new GroupMemberFeedback();
		feedback.setGroupMember(user);
		groupMemberFeedback.put(user, feedback);
	}
	
	public void removeIndividualFeedback(User user) {
		groupMemberFeedback.remove(user);
	}

	@Override
	public void accept(TaskVisitor v) {
		v.visit(this);
	}
	
}
