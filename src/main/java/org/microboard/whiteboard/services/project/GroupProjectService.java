package org.microboard.whiteboard.services.project;

import java.util.ArrayList;
import org.microboard.whiteboard.dto.assessment.GroupAssessmentDto;
import org.microboard.whiteboard.dto.project.GroupProjectDto;
import org.microboard.whiteboard.dto.user.MarkerGroupDto;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.pojos.ProjectEditApplyer;
import org.microboard.whiteboard.pojos.ProjectTemplateMaker;
import org.microboard.whiteboard.services.user.UnitDirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupProjectService extends BaseProjectService<GroupProject> {
	@Autowired
	private UnitDirectorService unitDirectorService;
	
	public void setProjectDtoUnit(GroupProjectDto projectDto) {
		for (GroupAssessmentDto assessment : projectDto.getAssessments()) {
			for (MarkerGroupDto markerDto : assessment.getGroupMarkerDtos()) {
				markerDto.setToMark(new ArrayList<>());
			}
		}
	}
	
	public GroupProjectDto getGroupProjectDto(Long id) {
		GroupProject soloProject = getProject(id);
		ProjectTemplateMaker templateMaker = new ProjectTemplateMaker();
		return templateMaker.getTemplate(soloProject);
	}
	
	public Long addProject(GroupProjectDto projectDto) {
		ProjectEditApplyer editApplyer = new ProjectEditApplyer();
		GroupProject newProject = new GroupProject();
		editApplyer.applyEdits(newProject, projectDto);
		UnitDirector creator = unitDirectorService.getLoggedInUser();
		
		creator.addProject(newProject);
		Long id = addProject(newProject);
		createGroupProjectUploadFolders(newProject);
		
		return id;
	}
	
	public void updateProject(GroupProjectDto projectDto) {
		ProjectEditApplyer editApplyer = new ProjectEditApplyer();
		GroupProject project = getProject(projectDto.getId());
		editApplyer.applyEdits(project, projectDto);
		updateProject(project);	
	}
	
	private void createGroupProjectUploadFolders(GroupProject project) {
		/*
		String path = System.getProperty("user.dir") + "/uploads/";
		path += Long.toString(project.getUnit().getId())+"/";
		List<User> users = project.getUnit().getCohort();
		for (User user : users) {
			String userPath = path + user.getUserName() + "/";
			userPath += project.getName() + "/";
			for (SoloAssessment assessment : project.getAssessments()) {
				new File(userPath + assessment.getName() + "/feedback/").mkdirs();
			}
		}
		*/
	}
} 
