package org.microboard.whiteboard.model.other;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.microboard.whiteboard.model.user.User;

@Entity
public class Log {
	@Id
	private Long id;
	private String action;
	private User user;
	private Date date;
}
