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
			
			User user = new Student();
			user.setUserName("james");
			user.setPassword(en.encode("test"));
			
			User user1 = new UnitDirector();
			
			user1.setUserName("b");
			user1.setPassword(en.encode("2"));
			
			userService.addUser(user);
			userService.addUser(user1);
	    }
}
