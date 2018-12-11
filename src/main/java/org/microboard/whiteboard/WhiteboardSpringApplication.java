package org.microboard.whiteboard;

import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.services.ProjectService;
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
	
	@Autowired
    private ProjectService projectService;
	
	
	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		BCryptPasswordEncoder en = new BCryptPasswordEncoder();

		Student student = new Student();
		student.setUserName("student");
		student.setPassword(en.encode("test"));

		UnitDirector unitDirector = new UnitDirector();

		unitDirector.setUserName("admin");
		unitDirector.setPassword(en.encode("test"));

		userService.addUser(student);
		userService.addUser(unitDirector);
		
		SoloProject sp = new SoloProject();
		sp.setName("Test Project 1");
		sp.setDescription("Description for \"Test Project 1\"");
		
		
		SoloAssessment sa1 = new SoloAssessment();
		sa1.setName("Test Assessment 1");
		sa1.setDescription("Description for \"Test Assessment 1\"");
		
		SoloAssessment sa2 = new SoloAssessment();
		sa2.setName("Test Assessment 2");
		sa2.setDescription("Description for \"Test Assessment 2\"");
		
		
		SoloTask st1 = new SoloTask();
		st1.setStatus("new");
		student.addTask(st1);
		
		SoloTask st2 = new SoloTask();
		st2.setStatus("new");
		student.addTask(st2);
		
		sa1.addTask(st1);
		sa2.addTask(st2);
		sp.addAssessment(sa1);
		sp.addAssessment(sa2);
		
		unitDirector.addProject(sp);
		
		userService.updateUser(unitDirector);
	}
}
