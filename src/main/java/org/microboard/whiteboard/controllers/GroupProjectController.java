package org.microboard.whiteboard.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.microboard.whiteboard.dto.assessment.GroupAssessmentDto;
import org.microboard.whiteboard.dto.assessment.GroupAssessmentDto;
import org.microboard.whiteboard.dto.project.GroupProjectDto;
import org.microboard.whiteboard.dto.project.GroupProjectDto;
import org.microboard.whiteboard.dto.user.MarkerGroupDto;
import org.microboard.whiteboard.dto.user.MarkerUserDto;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Group;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.services.project.GroupProjectService;
import org.microboard.whiteboard.services.project.GroupProjectService;
import org.microboard.whiteboard.services.user.AssessorService;
import org.microboard.whiteboard.services.user.UnitDirectorService;
import org.microboard.whiteboard.services.user.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/unit_director")
public class GroupProjectController {
	private String newGroupProjectPath = "unit_director/new_group_project";
	private String editGroupProjectPath = "unit_director/edit_group_project/";
	
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private AssessorService assessorService;
	
	@Autowired
	private UnitDirectorService unitDirectorService;
	
	@Autowired
	private GroupProjectService groupProjectService;
	
	
	@GetMapping("/edit_group_project/{id}")
	public String editGroupProject(Model model, @PathVariable Long id) {
		GroupProjectDto editProject = groupProjectService.getGroupProjectDto(id);
		model.addAttribute("groupProjectDto", editProject);
		model.addAttribute("path", editGroupProjectPath + id);
		return newGroupProjectPath;
	}
	
	@GetMapping("/new_group_project")
	public String getNewGroupProjectPage(Model model) {
		model.addAttribute("groupProjectDto", new GroupProjectDto());
		return newGroupProjectPath;
	}
	
	@PostMapping(value="/new_group_project", params={"addGroup"})
	public String addGroup(Model model, GroupProjectDto projectDto) {
		System.out.println("TEST");
		projectDto.getGroups().add(new Group());
	    return newGroupProjectPath;
	}
	
	@PostMapping(value="/new_group_project", params={"removeGroup"})
	public String removeGroup(Model model, GroupProjectDto projectDto, @RequestParam("removeGroup") int index) {
		projectDto.getGroups().remove(index);
	    return newGroupProjectPath;
	}

	@PostMapping(value="/new_group_project", params={"setUnit"})
	public String setUnit(Model model, GroupProjectDto projectDto) {
		groupProjectService.setProjectDtoUnit(projectDto);
	    return newGroupProjectPath;
	}
	
	@PostMapping(value="/new_group_project", params={"addAssessment"})
	public String addAssessment(Model model, GroupProjectDto project) {
		project.getAssessments().add(new GroupAssessmentDto());
	    return newGroupProjectPath;
	}
	
	@PostMapping(value="/new_group_project", params={"removeAssessment"})
	public String removeAssessment(Model model, GroupProjectDto project, @RequestParam("removeAssessment") int index) {
		project.getAssessments().remove(index);
	    return newGroupProjectPath;
	}
	
	@PostMapping(value="/new_group_project", params={"addMarker"})
	public String addMarker(Model model, GroupProjectDto project, @RequestParam("addMarker") int addMarker) {
		int assessmentIndex = addMarker;
		project.getAssessments().get(assessmentIndex).getGroupMarkerDtos().add(new MarkerGroupDto());
	    return newGroupProjectPath;
	}

	@PostMapping(value="/new_group_project", params={"removeMarker"})
	public String removeMarker(Model model, GroupProjectDto project, @RequestParam("removeMarker") List<Integer> removeMarker) {
		int assessmentIndex = removeMarker.get(0);
		int markerIndex = removeMarker.get(1);
		project.getAssessments().get(assessmentIndex).getGroupMarkerDtos().remove(markerIndex);
	    return newGroupProjectPath;
	}
	
	@PostMapping(value="/new_group_project", params= {"addProject"})
	public String addProject(Model model, GroupProjectDto projectDto) {
		if (! projectDto.validate()) {
			model.addAttribute("error",projectDto.getErrorMsg());
		} else {
			Long newId = groupProjectService.addProject(projectDto);
			return "redirect:/unit_director/edit_group_project/" + newId;
		}
		return newGroupProjectPath;
		
	}
	
	@PostMapping(value="/new_group_project", params= {"deleteProject"})
	public String deleteProject(Model model, GroupProjectDto projectDto) {
		if (! projectDto.canDelete()) {
			model.addAttribute("error", "You must delete all of this project's assessments before deleting this project.");
		} else {
			groupProjectService.deleteProject(projectDto.getId());
			return "redirect:/unit_director/projects";
		}
		return newGroupProjectPath;
		
	}
	
	@PostMapping(value="/new_group_project", params= {"saveAsNewProject"})
	public String saveAsNewProject(Model model, GroupProjectDto projectDto) {
		if (! projectDto.validate()) {
			model.addAttribute("error",projectDto.getErrorMsg());
			return newGroupProjectPath;
		} else {
			Long newId = groupProjectService.addProject(projectDto);
			return "redirect:/unit_director/edit_group_project/" + newId;
		}
		
	}
	
	@PostMapping(value="/new_group_project", params= {"editProject"})
	public String editProject(Model model, GroupProjectDto projectDto) {
		if (! projectDto.validate()) {
			model.addAttribute("error", projectDto.getErrorMsg());
		} else {
			groupProjectService.updateProject(projectDto);
		}
		return newGroupProjectPath;
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
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");   
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, null,  new CustomDateEditor(dateFormat, true));
	}
	
}