package org.microboard.whiteboard.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.project.dto.MarkerDto;
import org.microboard.whiteboard.model.project.dto.NewSoloAssessment;
import org.microboard.whiteboard.model.project.dto.NewSoloProject;
import org.microboard.whiteboard.model.project.dto.UserDto;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.HeaderGetter;
import org.microboard.whiteboard.model.user.visitors.SidebarGetter;
import org.microboard.whiteboard.services.project.ProjectService;
import org.microboard.whiteboard.services.user.AssessorService;
import org.microboard.whiteboard.services.user.UnitDirectorService;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/unit_director")
public class UnitDirectorController {
	
	private Logger logger = LoggerFactory.getLogger(UnitDirectorController.class);
	
	private String newProjectForm = "unit_director/new_project";
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AssessorService assessorService;
	
	@Autowired
	private UnitDirectorService unitDirectorService;
	
	@Autowired
	private ProjectService projectService;
	
	@GetMapping("/new_project")
	public String getNewSoloProjectPage(Model model) {
		NewSoloProject project = new NewSoloProject();
		model.addAttribute("newSoloProject", project);
		model.addAttribute("cohort", new ArrayList<UserDto>());
		return newProjectForm;
	}

	/*void createTasks(SoloProject project) {
		String path = (System.getProperty("user.dir") + "/uploads/");
		path += project.getUnit().getUnitCode()+"/";
		List<User> users = project.getUnit().getCohort();
		for (User user : users) {
			String userPath = path + user.getUserName() + "/";
			for (SoloAssessment assessment : project.getAssessments()) {
				new File(userPath + assessment.getName() + "/").mkdir();
			}
		}
	}*/

	@PostMapping(value="/new_solo_project", params={"setUnit"})
	public String setUnit(Model model, NewSoloProject project) {
		long unitId = project.getUnit().getId();
		Unit unit = unitService.getUnit(unitId).get();
		project.setUnit(unit);
		for (NewSoloAssessment assessment : project.getAssessments()) {
			for (MarkerDto markerDto : assessment.getMarkerDtos()) {
				markerDto.setToMark(new ArrayList<>());
			}
		}
	    return newProjectForm;
	}
	
	@PostMapping(value="/new_solo_project", params={"addAssessment"})
	public String addAssessment(Model model, NewSoloProject project) {
		project.getAssessments().add(new NewSoloAssessment());
	    return newProjectForm;
	}
	
	@PostMapping(value="/new_solo_project", params={"removeAssessment"})
	public String removeAssessment(Model model, NewSoloProject project, @RequestParam("removeAssessment") int index) {
		project.getAssessments().remove(index);
	    return newProjectForm;
	}
	
	@PostMapping(value="/new_solo_project", params={"addMarker"})
	public String addMarker(Model model, NewSoloProject project, @RequestParam("addMarker") int addMarker) {
		int assessmentIndex = addMarker;
		project.getAssessments().get(assessmentIndex).getMarkerDtos().add(new MarkerDto());
	    return newProjectForm;
	}

	@PostMapping(value="/new_solo_project", params={"removeMarker"})
	public String addMarker(Model model, NewSoloProject project, @RequestParam("removeMarker") List<Integer> removeMarker) {
		int assessmentIndex = removeMarker.get(0);
		int markerIndex = removeMarker.get(1);
		project.getAssessments().get(assessmentIndex).getMarkerDtos().remove(markerIndex);
	    return newProjectForm;
	}
	
	@PostMapping(value="/new_solo_project", params={"addProject"})
	public String addProject(Model model, NewSoloProject project) {
		SoloProject soloProject = new SoloProject();
		String name = project.getName();
		String description = project.getDescription();
		List<UnitDirector> helpers = new ArrayList<>();
		Unit unit = project.getUnit();
		
		soloProject.setName(name);
		soloProject.setDescription(description);
		soloProject.setHelpers(helpers);
		soloProject.setUnit(unit);
		
		/* TODO:
		 * Add ability to add helpers.
		 */
		
		
		for (NewSoloAssessment newAssessment : project.getAssessments()) {
			String assessmentName = newAssessment.getName();
			String assessmentDesc = newAssessment.getDescription();
			
			SoloAssessment soloAssessment = new SoloAssessment();
			soloAssessment.setName(assessmentName);
			soloAssessment.setDescription(assessmentDesc);
			
			for (User user : project.getUnit().getCohort()) {
				SoloTask soloTask = new SoloTask();
				user.addTask(soloTask);
				for (MarkerDto markerDto : newAssessment.getMarkerDtos()) {
					Assessor marker = markerDto.getMarker();
					if (markerDto.getToMark().contains(user)) {
						soloTask.addMarker(marker);
					}
				}
				soloAssessment.addTask(soloTask);
			}
			soloProject.addAssessment(soloAssessment);
		}
		projectService.addProject(soloProject);
		//createTasks(soloProject);
		UnitDirector creator = unitDirectorService.getLoggedInUser();
		creator.addProject(soloProject);
		
		unitDirectorService.updateUser(creator);
		
	    return newProjectForm;
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
	   return unitDirectorService.getLoggedInUser();
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
