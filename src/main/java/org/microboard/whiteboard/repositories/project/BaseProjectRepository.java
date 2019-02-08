package org.microboard.whiteboard.repositories.project;

import org.microboard.whiteboard.model.project.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseProjectRepository<T extends Project> extends CrudRepository<T, Long> {
}