package org.microboard.whiteboard.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.microboard.whiteboard.dto.assessment.SoloAssessmentDto;
import org.microboard.whiteboard.dto.project.SoloProjectDto;
import org.microboard.whiteboard.dto.user.MarkerUserDto;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.services.project.SoloProjectService;
import org.microboard.whiteboard.services.user.AssessorService;
import org.microboard.whiteboard.services.user.UnitDirectorService;
import org.microboard.whiteboard.services.user.UnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SoloProjectController {
	
	private Logger logger = LoggerFactory.getLogger(SoloProjectController.class);
	
	private String newSoloProjectPath = "unit_director/new_solo_project";
	private String editSoloProjectPath = "unit_director/edit_solo_project/";
	
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private AssessorService assessorService;
	
	@Autowired
	private UnitDirectorService unitDirectorService;
	
	@Autowired
	private SoloProjectService soloProjectService;
	
	
	@GetMapping("/edit_solo_project/{id}")
	public String editSoloProject(Model model, @PathVariable Long id) {
		SoloProjectDto editProject = soloProjectService.getSoloProjectDto(id);
		model.addAttribute("soloProjectDto", editProject);
		model.addAttribute("path", editSoloProjectPath + id);
		return newSoloProjectPath;
	}
	
	@GetMapping("/new_solo_project")
	public String getNewSoloProjectPage(Model model) {
		model.addAttribute("soloProjectDto", new SoloProjectDto());
		return newSoloProjectPath;
	}

	@PostMapping(value="/new_solo_project", params={"setUnit"})
	public String setUnit(Model model, SoloProjectDto projectDto) {
		soloProjectService.setProjectDtoUnit(projectDto);
	    return newSoloProjectPath;
	}
	
	@PostMapping(value="/new_solo_project", params={"addAssessment"})
	public String addAssessment(Model model, SoloProjectDto project) {
		project.getAssessments().add(new SoloAssessmentDto());
	    return newSoloProjectPath;
	}
	
	@PostMapping(value="/new_solo_project", params={"removeAssessment"})
	public String removeAssessment(Model model, SoloProjectDto project, @RequestParam("removeAssessment") int index) {
		project.getAssessments().remove(index);
	    return newSoloProjectPath;
	}
	
	@PostMapping(value="/new_solo_project", params={"addMarker"})
	public String addMarker(Model model, SoloProjectDto project, @RequestParam("addMarker") int addMarker) {
		int assessmentIndex = addMarker;
		project.getAssessments().get(assessmentIndex).getMarkerDtos().add(new MarkerUserDto());
	    return newSoloProjectPath;
	}

	@PostMapping(value="/new_solo_project", params={"removeMarker"})
	public String removeMarker(Model model, SoloProjectDto project, @RequestParam("removeMarker") List<Integer> removeMarker) {
		int assessmentIndex = removeMarker.get(0);
		int markerIndex = removeMarker.get(1);
		project.getAssessments().get(assessmentIndex).getMarkerDtos().remove(markerIndex);
	    return newSoloProjectPath;
	}
	
	@PostMapping(value="/new_solo_project", params= {"addProject"})
	public String addProject(Model model, SoloProjectDto projectDto) {
		if (! projectDto.validate()) {
			model.addAttribute("error",projectDto.getErrorMsg());
		} else {
			Long newId = soloProjectService.addProject(projectDto);
			return "redirect:/unit_director/edit_solo_project/" + newId;
		}
		return newSoloProjectPath;
		
	}
	
	@PostMapping(value="/new_solo_project", params= {"deleteProject"})
	public String deleteProject(Model model, SoloProjectDto projectDto) {
		if (! projectDto.canDelete()) {
			model.addAttribute("error", "You must delete all of this project's assessments before deleting this project.");
		} else {
			soloProjectService.deleteProject(projectDto.getId());
			return "redirect:/unit_director/projects";
		}
		return newSoloProjectPath;
		
	}
	
	@PostMapping(value="/new_solo_project", params= {"saveAsNewProject"})
	public String saveAsNewProject(Model model, SoloProjectDto projectDto) {
		if (! projectDto.validate()) {
			model.addAttribute("error",projectDto.getErrorMsg());
			return newSoloProjectPath;
		} else {
			Long newId = soloProjectService.addProject(projectDto);
			return "redirect:/unit_director/edit_solo_project/" + newId;
		}
		
	}
	
	@PostMapping(value="/new_solo_project", params= {"editProject"})
	public String editProject(Model model, SoloProjectDto projectDto) {
		if (! projectDto.validate()) {
			model.addAttribute("error", projectDto.getErrorMsg());
		} else {
			soloProjectService.updateProject(projectDto);
		}
		return newSoloProjectPath;
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