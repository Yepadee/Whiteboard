package org.microboard.whiteboard;

import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WhiteboardSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhiteboardSpringApplication.class, args);
	}
	@Autowired
    private UserService userService;
	
	
	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		BCryptPasswordEncoder en = new BCryptPasswordEncoder();

		User student = new Student();
		student.setUserName("student");
		student.setPassword(en.encode("test"));

		User unitDirector = new UnitDirector();

		unitDirector.setUserName("admin");
		unitDirector.setPassword(en.encode("test"));

		userService.addUser(student);
		userService.addUser(unitDirector);
	}
}
