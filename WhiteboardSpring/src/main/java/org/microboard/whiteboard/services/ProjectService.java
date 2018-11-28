package org.microboard.whiteboard.services;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	
	public List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<>();
		projectRepository.findAll().forEach(projects::add);
		return projects;
	}
	
	public void addProject(Project project) {
		projectRepository.save(project);
	}
} 
