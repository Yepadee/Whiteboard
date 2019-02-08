package org.microboard.whiteboard.services.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.repositories.project.BaseProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseProjectService<T extends Project> {

	@Autowired
	private BaseProjectRepository<T> projectRepository;
	
	public Optional<T> getProject(long id) {
		return projectRepository.findById(id);
	}
	
	public List<T> getAllProjects() {
		List<T> projects = new ArrayList<>();
		projectRepository.findAll().forEach(projects::add);
		return projects;
	}
		
	public void addProject(T project) {
		projectRepository.save(project);
	}
	
	public void updateProject(T project) {
		projectRepository.save(project);
	}
	
	public void deleteProject(long id) {
		projectRepository.deleteById(id);
	}
}
