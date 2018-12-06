package org.microboard.whiteboard.services;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.repositories.SoloProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoloProjectService {
	@Autowired
	private SoloProjectRepository soloProjectRepository;
	
	public List<SoloProject> getAllProjects() {
		List<SoloProject> projects = new ArrayList<>();
		soloProjectRepository.findAll().forEach(projects::add);
		return projects;
	}
	
	public void addProject(SoloProject project) {
		soloProjectRepository.save(project);
	}
}
