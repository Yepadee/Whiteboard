package org.microboard.whiteboard.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.microboard.whiteboard.dto.project.ProjectDto;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.services.user.AssessorService;
import org.microboard.whiteboard.services.user.UnitDirectorService;
import org.microboard.whiteboard.services.user.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/unit_director")
public abstract class ProjectController<T extends ProjectDto<?>> {
	protected String viewProjectsPath = "/unit_director/projects";
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private AssessorService assessorService;
	
	@Autowired
	private UnitDirectorService unitDirectorService;
	
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
	
	@ModelAttribute("unitDirectorList")
	public List<UnitDirector> getUnitDirectorList() {
	   return unitDirectorService.getAllUsers();
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");   
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, null,  new CustomDateEditor(dateFormat, true));
	}
	
	public abstract String editProject(Model model, @PathVariable Long id);
	
	public abstract String setUnit(T projectDto);
	
	public abstract String addAssessment(T project);
	
	public abstract String removeAssessment(T project, @RequestParam("removeAssessment") int index);
	
	public abstract String addMarker(T project, @RequestParam("addMarker") int index);

	public abstract String removeMarker(T project, @RequestParam("removeMarker") List<Integer> removeMarker);
	
	public abstract String addProject(Model model, T projectDto);
	
	public abstract String deleteProject(Model model, T projectDto);
	
	public abstract String saveAsNewProject(Model model, T projectDto);
	
	public abstract String editProject(Model model, T projectDto);
}
