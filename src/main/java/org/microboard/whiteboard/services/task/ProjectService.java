package org.microboard.whiteboard.services.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.repositories.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public Optional<Project> getProject(long id) {
		return projectRepository.findById(id);
	}
	
	public List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<>();
		projectRepository.findAll().forEach(projects::add);
		return projects;
	}
	
	
	public void addProject(Project project) {
		projectRepository.save(project);
	}
	
	public void updateProject(Project project) {
		projectRepository.save(project);
	}
	
	public void deleteProject(long id) {
		projectRepository.deleteById(id);
	}
} 
