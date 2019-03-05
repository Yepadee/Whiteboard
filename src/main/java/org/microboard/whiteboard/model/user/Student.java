package org.microboard.whiteboard.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.microboard.whiteboard.model.user.visitors.UserVisitor;

@DiscriminatorValue("student")
@Entity
public class Student extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4005774783951106434L;

	public Student() {}
	
	public Student(User user) {
		super(user);
	}
	
	public Student(String name, String password) {
		this.setUserName(name);
		this.setPassword(password);
	}

	@Override
	public void accept(UserVisitor v) {
		v.visit(this);
	}
	
}
