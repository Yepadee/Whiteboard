package org.microboard.whiteboard.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.services.user.AssessorService;
import org.microboard.whiteboard.services.user.UnitService;
import org.microboard.whiteboard.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/unit_director")
public class UnitDirectorController {
	
	Logger logger = LoggerFactory.getLogger(UnitDirectorController.class);
	
	@Autowired
	private AssessorService assessorService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/new_solo_project")
	public String getNewSoloProjectPage(Model model) {
		SoloProject p = new SoloProject();
		model.addAttribute("soloProject", p);
		
		return "new_project_test";
	}
	
	@PostMapping(value="/new_solo_project", params={"addAssessment"})
	public String addRow(final SoloProject project) {
		SoloAssessment assessment = new SoloAssessment();
		Unit unit = unitService.getUnit(project.getUnit().getId()).get();

		for (User user : unit.getCohort()) {
			SoloTask task = new SoloTask();
			task.setAccountable(user);
			assessment.addTask(task);
		}
		
		project.getAssessments().add(assessment);
	    return "new_project_test";
	}
	
	@PostMapping(value="/new_solo_project", params={"removeAssessment"})
	public String addRow(final SoloProject project, @RequestParam("removeAssessment") int index) {
		project.getAssessments().remove(index);
	    return "new_project_test";
	}
	
	@PostMapping(value="/new_solo_project", params={"setUnit"})
	public String setUnit(Model model, final SoloProject project) {
		Unit unit = unitService.getUnit(project.getUnit().getId()).get();
		System.out.println("TEST");
		for (SoloAssessment assessment : project.getAssessments()) {
			assessment.setTasks(new ArrayList<SoloTask>());
			for (User user : unit.getCohort()) {
				SoloTask task = new SoloTask();
				task.setAccountable(user);
				assessment.addTask(task);
			}
		}
	    return "new_project_test";
	}
	
	@PostMapping(value="/new_solo_project", params={"addMarker"})
	public String addMarker(Model model, final SoloProject project, @RequestParam("addMarker") List<Integer> addMarker) {
		int assessmentIndex = addMarker.get(0);
		int taskIndex = addMarker.get(1);
		
		project.getAssessments().get(assessmentIndex).getTasks().get(taskIndex).addMarker(new Assessor());
		
	    return "new_project_test";
	}
	
	@PostMapping(value="/new_solo_project", params={"removeMarker"})
	public String removeMarker(Model model, final SoloProject project, @RequestParam("removeMarker") List<Integer> removeMarker) {
		int assessmentIndex = removeMarker.get(0);
		int taskIndex = removeMarker.get(1);
		int markerIndex = removeMarker.get(2);
		
		project.getAssessments().get(assessmentIndex).getTasks().get(taskIndex).getMarkers().remove(markerIndex);
		
	    return "new_project_test";
	}
	
	@GetMapping("/edit_project/{id}")
	public String editProjectPage() {
		return "edit_project";
	}
	
	@GetMapping("/projects")
	public String viewProjectsPage() {
		return "view_projects";
	}
	
	@GetMapping("/manage_permissions")
	public String managePermissionsPage() {
		return "manage_permissions";
	}
	
	@ModelAttribute("user")
	public UnitDirector getUnitDirector() {
	   return (UnitDirector) userService.getLoggedInUser();
	}
	
	@ModelAttribute("unitList")
	public List<Unit> getUnitList() {
	   return unitService.getAllUnits();
	}
	
	@ModelAttribute("assessorList")
	public List<Assessor> getAssessorList() {
	   return assessorService.getAllUsers();
	}
	
}
