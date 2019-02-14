package org.microboard.whiteboard.model.feedback;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.User;

@Entity
public class GroupMemberFeedback {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
	private long id;
	
	@ManyToOne
	private User groupMember;

	@OneToMany
	@JoinTable(name="group_member_feedback_feedback", joinColumns=@JoinColumn(name="group_member_feedback_id"))
	@MapKeyColumn(name="assessor_id")
	private Map<Assessor, Feedback> feedback = new HashMap<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getGroupMember() {
		return groupMember;
	}

	public void setGroupMember(User groupMember) {
		this.groupMember = groupMember;
	}

	public Map<Assessor, Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(Map<Assessor, Feedback> feedback) {
		this.feedback = feedback;
	}
	
	
}
