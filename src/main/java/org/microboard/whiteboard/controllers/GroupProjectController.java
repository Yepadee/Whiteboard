package org.microboard.whiteboard.controllers;

import java.util.List;

import org.microboard.whiteboard.dto.project.GroupProjectDto;
import org.microboard.whiteboard.services.project.GroupProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupProjectController extends ProjectController<GroupProjectDto> {
	private String newGroupProjectPath = "unit_director/new_group_project";
	private String editGroupProjectPath = "unit_director/edit_group_project";
	
	@Autowired
	private GroupProjectService groupProjectService;
	
	@GetMapping("/new_group_project")
	public String getNewProjectPage(Model model) {
		model.addAttribute("groupProjectDto", new GroupProjectDto());
		return newGroupProjectPath;
	}
	
	@GetMapping("/edit_group_project/{id}")
	public String editProject(Model model, @PathVariable Long id) {
		GroupProjectDto editProject = groupProjectService.getGroupProjectDto(id);
		model.addAttribute("groupProjectDto", editProject);
		model.addAttribute("path", editGroupProjectPath + id);
		return newGroupProjectPath;
	}

	@PostMapping(value="/new_group_project", params={"setUnit"})
	public String setUnit(GroupProjectDto projectDto) {
		projectDto.changeUnit();
	    return newGroupProjectPath;
	}
	
	@PostMapping(value="/new_group_project", params={"addAssessment"})
	public String addAssessment(GroupProjectDto project) {
		project.addAssessment();
	    return newGroupProjectPath;
	}
	
	@PostMapping(value="/new_group_project", params={"removeAssessment"})
	public String removeAssessment(GroupProjectDto project, @RequestParam("removeAssessment") int index) {
		project.getAssessments().remove(index);
	    return newGroupProjectPath;
	}
	
	@PostMapping(value="/new_group_project", params={"addMarker"})
	public String addMarker(GroupProjectDto project, @RequestParam("addMarker") int index) {
		project.addMarker(index);
	    return newGroupProjectPath;
	}

	@PostMapping(value="/new_group_project", params={"removeMarker"})
	public String removeMarker(GroupProjectDto project, @RequestParam("removeMarker") List<Integer> removeMarker) {
		int assessmentIndex = removeMarker.get(0);
		int markerIndex = removeMarker.get(1);
		project.removeMarker(assessmentIndex, markerIndex);
	    return newGroupProjectPath;
	}
	
	@PostMapping(value="/new_group_project", params= {"addProject"})
	public String addProject(Model model, GroupProjectDto projectDto) {
		if (! projectDto.validate()) {
			model.addAttribute("error",projectDto.getErrorMsg());
		} else {
			Long newId = groupProjectService.addProject(projectDto);
			return "redirect:/" + editGroupProjectPath + "/" + newId;
		}
		return newGroupProjectPath;
		
	}
	
	@PostMapping(value="/new_group_project", params= {"deleteProject"})
	public String deleteProject(Model model, GroupProjectDto projectDto) {
		if (! projectDto.canDelete()) {
			model.addAttribute("error", "You must delete all of this project's assessments before deleting this project.");
		} else {
			groupProjectService.deleteProject(projectDto.getId());
			return "redirect:/" + newGroupProjectPath;
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
			return "redirect:/" + editGroupProjectPath + "/" + newId;
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
	
	
	@PostMapping(value="/new_group_project", params={"addGroup"})
	public String addGroup(GroupProjectDto projectDto) {
		projectDto.addGroup();
	    return newGroupProjectPath;
	}
	
	
	@PostMapping(value="/new_group_project", params={"removeGroup"})
	public String removeGroup(GroupProjectDto projectDto, @RequestParam("removeGroup") int index) {
		projectDto.removeGroup(index);
	    return newGroupProjectPath;
	}
}