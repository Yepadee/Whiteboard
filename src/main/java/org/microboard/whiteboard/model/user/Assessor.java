package org.microboard.whiteboard.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.microboard.whiteboard.model.user.visitors.UserVisitor;

@DiscriminatorValue("assessor")
@Entity
public class Assessor extends User {

	@Override
	public void accept(UserVisitor v) {
		v.visit(this);
	}
	
}
