package org.microboard.whiteboard.controllers;

import org.microboard.whiteboard.dto.user.SelectedUsersDto;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.model.user.visitors.UserPermChangeValidator;
import org.microboard.whiteboard.services.project.GroupProjectService;
import org.microboard.whiteboard.services.project.SoloProjectService;
import org.microboard.whiteboard.services.user.AssessorService;
import org.microboard.whiteboard.services.user.StudentService;
import org.microboard.whiteboard.services.user.UnitDirectorService;
import org.microboard.whiteboard.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unit_director")
public class UnitDirectorController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private AssessorService assessorService;
	
	@Autowired
	private UnitDirectorService unitDirectorService;
	
	@Autowired
	private SoloProjectService soloProjectService;
	
	@Autowired
	private GroupProjectService groupProjectService;
	
	
	@GetMapping("/projects")
	public String viewProjectsPage(Model model) {
		UnitDirector unitDirector = unitDirectorService.getLoggedInUser();
		model.addAttribute("soloProjects", soloProjectService.getByCreator(unitDirector));
		model.addAttribute("groupProjects", groupProjectService.getByCreator(unitDirector));
		return "unit_director/view_projects";
	}
	
	@GetMapping("/manage_perms")
	public String managePermissionsPage(Model model) {
		model.addAttribute("selectedUsersDto", new SelectedUsersDto());
		model.addAttribute("students", studentService.getAllUsers());
		model.addAttribute("assessors", assessorService.getAllUsers());
		model.addAttribute("unitDirectors", unitDirectorService.getAllUsers());
		return "unit_director/manage_perms";
	}
	
	@PostMapping(value="/manage_perms")
	public String setAssessor(Model model, SelectedUsersDto selectedUsersDto) {
		String newPerms = selectedUsersDto.getNewPerms();
		UserPermChangeValidator validator = new UserPermChangeValidator(newPerms);
		boolean error = false;
		String errorMsg = "";
		for (User user : selectedUsersDto.getSelectedUsers()) {
			user.accept(validator);
			boolean valid = validator.getResult();
			if (valid && getUnitDirector().getId() != user.getId()) {
				userService.detachUser(user);
				userService.changePerms(user.getId(), newPerms);
				User assessor = userService.getUser(user.getId());
				userService.persistUser(assessor);	

			} else {
				error = true;
				errorMsg += "You may not change your own permissions.";
			}
		}
		if (error) {
			errorMsg += validator.getErrorMsg();
			model.addAttribute("errorMsg", errorMsg);
		}
		
		model.addAttribute("selectedUsersDto", new SelectedUsersDto());
		model.addAttribute("students", studentService.getAllUsers());
		model.addAttribute("assessors", assessorService.getAllUsers());
		model.addAttribute("unitDirectors", unitDirectorService.getAllUsers());
	    return "unit_director/manage_perms";
	}
	
	
	@ModelAttribute("user")
	public UnitDirector getUnitDirector() {
	   return unitDirectorService.getLoggedInUser();
	}
	
}
