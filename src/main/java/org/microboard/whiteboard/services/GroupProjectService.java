package org.microboard.whiteboard.services;

import java.util.ArrayList;
import java.util.List;

import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.repositories.GroupProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupProjectService {
	@Autowired
	private GroupProjectRepository groupProjectRepository;
	
	public List<GroupProject> getAllProjects() {
		List<GroupProject> projects = new ArrayList<>();
		groupProjectRepository.findAll().forEach(projects::add);
		return projects;
	}
	
	public void addProject(GroupProject project) {
		groupProjectRepository.save(project);
	}
}
