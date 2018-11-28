package org.microboard.whiteboard.model.assessment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("solo")
public class SoloAssessment extends Assessment{
	//@ManyToOne
	//private SoloProject project;
}
