package org.microboard.whiteboard.services.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.dto.project.ProjectDto;
import org.microboard.whiteboard.dto.project.visitors.ProjectEditFiller;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.visitors.ProjectFolderCreator;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.repositories.project.BaseProjectRepository;
import org.microboard.whiteboard.services.user.UnitDirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseProjectService<T extends Project> {

	@Autowired
	private BaseProjectRepository<T> projectRepository;
	
	@Autowired
	private UnitDirectorService unitDirectorService;
	
	public T getProject(long id) throws RuntimeException {
		Optional<T> maybeProject = projectRepository.findById(id);
		if (maybeProject.isPresent()) {
			return maybeProject.get();
		} else {
			throw new RuntimeException("Project not found with id \'" + id + "\'");
		}
		
	}
	
	public List<T> getAllProjects() {
		List<T> projects = new ArrayList<>();
		projectRepository.findAll().forEach(projects::add);
		return projects;
	}
		
	public Long addProject(T project) {
		return projectRepository.save(project).getId();
	}
	
	public Long addProject(ProjectDto<T> projectDto) {
		T newProject = projectDto.toProject();
		UnitDirector creator = unitDirectorService.getLoggedInUser();
		creator.addProject(newProject);
		Long id = addProject(newProject);
		createProjectUploadFolders(newProject);
		
		return id;
	}
	
	public void updateProject(ProjectDto<T> projectDto) {
		T project = getProject(projectDto.getId());
		ProjectEditFiller editFiller = new ProjectEditFiller(project);
		projectDto.accept(editFiller);
		updateProject(project);
	}
	
	private void createProjectUploadFolders(T project) {
		ProjectFolderCreator folderCreater = new ProjectFolderCreator();
		project.accept(folderCreater);
	}
	
	public void updateProject(T project) {
		projectRepository.save(project);
	}
	
	public void deleteProject(long id) {
		projectRepository.deleteById(id);
	}
	
	public List<T> getByCreator(UnitDirector creator) {
		return projectRepository.findByCreator(creator);
	}
	
	
}
