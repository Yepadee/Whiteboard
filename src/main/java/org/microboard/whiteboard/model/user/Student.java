package org.microboard.whiteboard.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("student")
@Entity
public class Student extends User {
	
	public Student() {
		
	}
	
	public Student(String name, String password) {
		this.setName(name);
		this.setPassword(password);
	}
}
