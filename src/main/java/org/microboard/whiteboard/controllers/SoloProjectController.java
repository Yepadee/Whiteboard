package org.microboard.whiteboard.controllers;

import java.util.List;

import org.microboard.whiteboard.dto.project.SoloProjectDto;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.project.visitors.ProjectTemplateFiller;
import org.microboard.whiteboard.services.project.SoloProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SoloProjectController extends ProjectController<SoloProjectDto> {

	private String newSoloProjectPath = "unit_director/new_solo_project";
	private String editSoloProjectPath = "unit_director/edit_solo_project";
	
	@GetMapping("/new_solo_project")
	public String getNewSoloProjectPage(Model model) {
		model.addAttribute("soloProjectDto", new SoloProjectDto());
		return newSoloProjectPath;
	}
	
	
	@Autowired
	private SoloProjectService projectService;
	
	@GetMapping("/edit_solo_project/{id}")
	public String editProject(Model model, @PathVariable Long id) {
		ProjectTemplateFiller templateFiller = new ProjectTemplateFiller(model);
		SoloProject project = projectService.getProject(id);
		project.accept(templateFiller);
		return newSoloProjectPath;
	}

	@PostMapping(value="/new_solo_project", params={"setUnit"})
	public String setUnit(SoloProjectDto projectDto) {
		projectDto.changeUnit();
	    return newSoloProjectPath;
	}
	
	@PostMapping(value="/new_solo_project", params={"addAssessment"})
	public String addAssessment(SoloProjectDto project) {
		project.addAssessment();
	    return newSoloProjectPath;
	}
	
	@PostMapping(value="/new_solo_project", params={"removeAssessment"})
	public String removeAssessment(SoloProjectDto project, @RequestParam("removeAssessment") int index) {
		project.removeAssessment(index);
	    return newSoloProjectPath;
	}
	
	@PostMapping(value="/new_solo_project", params={"addMarker"})
	public String addMarker(SoloProjectDto project, @RequestParam("addMarker") int index) {
		project.addMarker(index);
	    return newSoloProjectPath;
	}

	@PostMapping(value="/new_solo_project", params={"removeMarker"})
	public String removeMarker(SoloProjectDto project, @RequestParam("removeMarker") List<Integer> removeMarker) {
		int assessmentIndex = removeMarker.get(0);
		int markerIndex = removeMarker.get(1);
		project.removeMarker(assessmentIndex, markerIndex);
	    return newSoloProjectPath;
	}
	
	@PostMapping(value="/new_solo_project", params= {"addProject"})
	public String addProject(Model model, SoloProjectDto projectDto) {
		if (! projectDto.validate()) {
			model.addAttribute("error", projectDto.getErrorMsg());
		} else {
			Long newId = projectService.addProject(projectDto);
			return "redirect:" + "/" + editSoloProjectPath + "/" + newId;
		}
		return newSoloProjectPath;
		
	}
	
	@PostMapping(value="/new_solo_project", params= {"deleteProject"})
	public String deleteProject(Model model, SoloProjectDto projectDto) {
		if (! projectDto.canDelete()) {
			model.addAttribute("error", projectDto.getErrorMsg());
		} else {
			projectService.deleteProject(projectDto.getId());
			return "redirect:" + viewProjectsPath;
		}
		return newSoloProjectPath;
		
	}
	
	@PostMapping(value="/new_solo_project", params= {"saveAsNewProject"})
	public String saveAsNewProject(Model model, SoloProjectDto projectDto) {
		if (! projectDto.validate()) {
			model.addAttribute("error", projectDto.getErrorMsg());
			return newSoloProjectPath;
		} else {
			Long newId = projectService.addProject(projectDto);
			return "redirect:" + "/" + editSoloProjectPath + "/" + newId;
		}
		
	}
	
	@PostMapping(value="/new_solo_project", params= {"editProject"})
	public String editProject(Model model, SoloProjectDto projectDto) {
		if (! projectDto.validate()) {
			model.addAttribute("error", projectDto.getErrorMsg());
		} else {
			projectService.updateProject(projectDto);
		}
		return newSoloProjectPath;
	}
	
}