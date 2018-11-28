package org.microboard.whiteboard.model.assessment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("group")
public class GroupAssessment extends Assessment {
	
	//@ManyToOne
	//private GroupProject project;
}
