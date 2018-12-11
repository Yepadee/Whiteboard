package org.microboard.whiteboard;

import org.microboard.whiteboard.model.assessment.GroupAssessment;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.GroupTask;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Group;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.repositories.GroupRepository;
import org.microboard.whiteboard.repositories.TaskRepository;
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
	
	
	@Autowired
	private GroupRepository groupRepository;
	
	
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
		sp.setName("Test Solo Project 1");
		sp.setDescription("Description for \"Test Solo Project 1\"");
		
		SoloAssessment sa1 = new SoloAssessment();
		sa1.setName("Test Solo Assessment 1");
		sa1.setDescription("Description for \"Test Solo Assessment 1\"");
		sp.addAssessment(sa1);
		
		SoloAssessment sa2 = new SoloAssessment();
		sa2.setName("Test Solo Assessment 2");
		sa2.setDescription("Description for \"Test Solo Assessment 2\"");
		sp.addAssessment(sa2);
		
		SoloTask st1 = new SoloTask();
		st1.setStatus("new");
		student.addTask(st1);
		sa1.addTask(st1);
		
		SoloTask st2 = new SoloTask();
		st2.setStatus("new");
		student.addTask(st2);
		sa2.addTask(st2);
		
		unitDirector.addProject(sp);
		userService.updateUser(unitDirector);
		userService.updateUser(student);
		
		
		/*
		GroupProject gp = new GroupProject();
		gp.setName("Test Group Project 1");
		gp.setDescription("Description for \"Test Group Project 1\"");
		
		
		Group g = new Group();
		g.setName("Test group 1");
		g.addMember(student);
		gp.addGroup(g);
		
		
		GroupAssessment ga1 = new GroupAssessment();
		ga1.setName("Test Group Assessment 1");
		ga1.setDescription("Description for \"Test Group Assessment 1\"");
		gp.addAssessment(ga1);
		
		
		GroupAssessment ga2 = new GroupAssessment();
		ga2.setName("Test Group Assessment 2");
		ga2.setDescription("Description for \"Test Group Assessment 2\"");
		gp.addAssessment(ga2);
		
		
		GroupTask gt1 = new GroupTask();
		gt1.setStatus("new");
		g.addTask(gt1);
		ga1.addTask(gt1);
		
		
		GroupTask gt2 = new GroupTask();
		gt2.setStatus("new");
		g.addTask(gt2);
		ga2.addTask(gt2);
		
		
		//TODO: Remove cascades from children
		
		projectService.addProject(gp);
		unitDirector.addProject(gp);
		*/
		
		userService.updateUser(unitDirector);
		userService.updateUser(unitDirector);
		userService.updateUser(unitDirector);

		userService.updateUser(student);
		userService.updateUser(student);
		userService.updateUser(student);
	}
}
