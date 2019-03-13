package org.microboard.whiteboard.services.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.dto.assessment.SoloAssessmentDto;
import org.microboard.whiteboard.dto.project.SoloProjectDto;
import org.microboard.whiteboard.dto.user.MarkerUserDto;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.pojos.ProjectEditApplyer;
import org.microboard.whiteboard.pojos.ProjectTemplateMaker;
import org.microboard.whiteboard.services.user.UnitDirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoloProjectService extends BaseProjectService<SoloProject> {
	@Autowired
	private UnitDirectorService unitDirectorService;
	
	public void setProjectDtoUnit(SoloProjectDto projectDto) {
		for (SoloAssessmentDto assessment : projectDto.getAssessments()) {
			for (MarkerUserDto markerDto : assessment.getMarkerDtos()) {
				markerDto.setToMark(new ArrayList<>());
			}
		}
	}
	
	public SoloProjectDto getSoloProjectDto(long id) {
		SoloProject soloProject = getProject(id);
		ProjectTemplateMaker templateMaker = new ProjectTemplateMaker();
		return templateMaker.getTemplate(soloProject);
	}
	
	public Long addProject(SoloProjectDto projectDto) {
		ProjectEditApplyer editApplyer = new ProjectEditApplyer();
		SoloProject newProject = new SoloProject();
		editApplyer.applyEdits(newProject, projectDto);
		UnitDirector creator = unitDirectorService.getLoggedInUser();
		
		creator.addProject(newProject);
		Long id = addProject(newProject);
		createSoloProjectUploadFolders(newProject);
		
		return id;
	}
	
	public void updateProject(SoloProjectDto projectDto) {
		ProjectEditApplyer editApplyer = new ProjectEditApplyer();
		SoloProject project = getProject(projectDto.getId());
		editApplyer.applyEdits(project, projectDto);
		updateProject(project);	
	}
	
	private void createSoloProjectUploadFolders(SoloProject project) {
		String path = System.getProperty("user.dir") + "/uploads/";
		path += Long.toString(project.getUnit().getId())+"/";
		List<User> users = project.getUnit().getCohort();
		for (User user : users) {
			String userPath = path + user.getUserName() + "/";
			userPath += project.getName() + "/";
			for (SoloAssessment assessment : project.getAssessments()) {
				//System.out.println(userPath + assessment.getName() + "/");
				new File(userPath + assessment.getName() + "/feedback/").mkdirs();
			}
		}
	}
} 
