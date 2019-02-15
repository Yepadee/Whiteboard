package org.microboard.whiteboard;

import org.microboard.whiteboard.model.assessment.GroupAssessment;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.GroupTask;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Group;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.services.project.ProjectService;
import org.microboard.whiteboard.services.user.UnitService;
import org.microboard.whiteboard.services.user.UserService;
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
    private UnitService unitService;
	
	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		BCryptPasswordEncoder en = new BCryptPasswordEncoder();

		Student student = new Student();
		student.setUserName("student");
		student.setPassword(en.encode("test"));

		UnitDirector unitDirector = new UnitDirector();
		unitDirector.setUserName("admin");
		unitDirector.setPassword(en.encode("test"));
		
		UnitDirector aydin = new UnitDirector();
		aydin.setUserName("Aydin");
		aydin.setPassword(en.encode("test"));
		
		Assessor assessor = new Assessor();
		assessor.setUserName("assessor");
		assessor.setPassword(en.encode("test"));

		userService.addUser(student);
		userService.addUser(unitDirector);
		userService.addUser(aydin);
		userService.addUser(assessor);
		
		int numUsers = 5;
		Unit unit1 = new Unit();
		unit1.setUnitName("Computer Science in Society");
		unit1.setUnitCode("COMS20003");
		for (int i = 1; i <= numUsers; i ++) {
			User s = new Student();
			s.setUserName("User " + i);
			userService.addUser(s);
			unit1.addUser(s);
		}
		
		unit1.addUser(student);
		unit1.addUser(unitDirector);
		unit1.addUser(aydin);
		
		
		unitService.addUnit(unit1);
		
		Unit unit2 = new Unit();
		unit2.setUnitName("Signals, Patterns and Symbols");
		unit1.setUnitCode("COMS20005");
		for (int i = numUsers + 1; i <= numUsers * 2; i ++) {
			User s = new Student();
			s.setUserName("User " + i);
			userService.addUser(s);
			unit2.addUser(s);
		}
		unitService.addUnit(unit2);
		

		
		SoloProject sp = new SoloProject();
		sp.setName("Test Solo Project 1");
		sp.setDescription("Description for \"Test Solo Project 1\"");
		sp.setUnit(unit1);
		
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
		sa1.addTask(st1);
		student.addTask(st1);
		unitDirector.addTask(st1);
		
		st1.addMarker(assessor);
		
		
		SoloTask st2 = new SoloTask();
		st2.setStatus("new");
		student.addTask(st2);
		sa2.addTask(st2);
		
		SoloTask stu = new SoloTask();
		stu.setStatus("new");
		unitDirector.addTask(stu);
		sa2.addTask(stu);
		
		
		
		projectService.addProject(sp);
		unitDirector.addProject(sp);
		
		
		
		GroupProject gp = new GroupProject();
		gp.setName("Test Group Project 1");
		gp.setDescription("Description for \"Test Group Project 1\"");
		gp.setUnit(unit1);
		
		
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
		
		unitDirector.addProject(gp);
		projectService.updateProject(gp);
		projectService.updateProject(sp);
		
		//TODO: if constraint broken on user_solo_tasks then add cascade type back to tasks in user.
		
	}
}
