package org.microboard.whiteboard.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("assessor")
@Entity
public class Assessor extends User {
	
}
