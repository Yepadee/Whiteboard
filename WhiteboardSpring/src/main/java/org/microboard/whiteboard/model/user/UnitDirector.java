package org.microboard.whiteboard.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("unit director")
@Entity
public class UnitDirector extends User {

}
