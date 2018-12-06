package org.microboard.whiteboard.repositories;

import org.microboard.whiteboard.model.project.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
	
}
