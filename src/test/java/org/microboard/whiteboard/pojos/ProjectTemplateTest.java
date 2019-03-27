package org.microboard.whiteboard.pojos;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.microboard.whiteboard.dto.project.SoloProjectDto;
import org.microboard.whiteboard.dto.user.MarkerUserDto;
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

public class ProjectTemplateTest {

	@Test
	public void projectTemplateBijective_Test() {
		ProjectTemplateMaker ptm = new ProjectTemplateMaker();
		ProjectEditApplyer pea = new ProjectEditApplyer();
		
		SoloProject sp = buildSoloProject();
		SoloProject r = new SoloProject();
		
		SoloProjectDto spDto = ptm.getTemplate(sp);
		pea.applyEdits(r, spDto);
		
		//assertTrue(sp.getId() == r.getId());
		assertTrue(sp.getName() == r.getName());
		assertTrue(sp.getUnit().getId() == r.getUnit().getId());
		//assertTrue(sp.getCreator().getId() == r.getCreator().getId());
		for (int i = 0; i < sp.getAssessments().size(); i ++) {
			SoloAssessment a = sp.getAssessments().get(i);
			SoloAssessment ar = r.getAssessments().get(i);

			//assertTrue(a.getId() == ar.getId());
			for (int j = 0; j < a.getTasks().size(); j ++) {
				SoloTask t = a.getTasks().get(j);
				SoloTask tr = ar.getTasks().get(j);
				Iterator<Assessor> it1 = t.getMarkers().iterator();
				for (int k = 0; k < t.getMarkers().size(); k ++) {
					Assessor ass = ((List<Assessor>) t.getMarkers()).get(k);
					Assessor assr = ((List<Assessor>) tr.getMarkers()).get(k);
					
					assertTrue(ass.getId() == assr.getId());
					
				}
				
				//assertTrue(t.getId() == tr.getId());
				assertTrue(t.getAccountable().getId() == tr.getAccountable().getId());
			}
		}
		
		
		r = sp;
		pea.applyEdits(r, spDto);
		
		for (int i = 0; i < sp.getAssessments().size(); i ++) {
			SoloAssessment a = sp.getAssessments().get(i);
			SoloAssessment ar = r.getAssessments().get(i);

			assertTrue(a.getId() == ar.getId());
			for (int j = 0; j < a.getTasks().size(); j ++) {
				SoloTask t = a.getTasks().get(j);
				SoloTask tr = ar.getTasks().get(j);

				assertTrue(t.getId() == tr.getId());
				assertTrue(t.getAccountable().getId() == tr.getAccountable().getId());
			}
		}
		
	}

	private SoloProject buildSoloProject() {
		int numUsers = 5;
		int numAssessments = 5;
		int numMarkers = 3;
		
		Unit unit1 = new Unit();
		unit1.setId(1L);
		unit1.setUnitName("Computer Science in Society");
		unit1.setUnitCode("COMS20003");
		for (int i = 1; i <= numUsers; i ++) {
			User s = new Student();
			s.setUserName("User " + i);
			unit1.addUser(s);
		}
		
		SoloProject sp = new SoloProject();
		sp.setId(1L);
		sp.setName("Test Solo Project 1");
		sp.setDescription("Description for \"Test Solo Project 1\"");
		sp.setUnit(unit1);
		
		for (int j = 0; j < numAssessments; j ++) {
			SoloAssessment a = new SoloAssessment();
			//a.setId(1L);
			a.setName("Test Solo Assessment " + j);
			a.setDescription("Description for \"Test Solo Assessment " + j + "\"");
			sp.addAssessment(a);
			
			Long id = 0L;
			
			for (User user : unit1.getCohort()) {
				SoloTask soloTask = new SoloTask();
				
				for (int i = 0; i < numMarkers; i ++) {
					Assessor ass = new Assessor();
					ass.setId(id ++);
					soloTask.addMarker(ass);
				}
				
				a.addTask(soloTask);
				soloTask.setAccountable(user);
			}
		}
		
		UnitDirector ud = new UnitDirector();
		ud.setId(1L);
		ud.addProject(sp);
		
		return sp;
	}
	
	private GroupProject buildGroupProject() {
		Unit unit1 = new Unit();
		unit1.setUnitName("Computer Science in Society");
		unit1.setUnitCode("COMS20003");
		
		GroupProject gp = new GroupProject();
		gp.setName("Test Group Project 1");
		gp.setDescription("Description for \"Test Group Project 1\"");
		gp.setUnit(unit1);
		
		
		Group g = new Group();
		g.setName("Test group 1");
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
		
		return gp;
		
	}
}
