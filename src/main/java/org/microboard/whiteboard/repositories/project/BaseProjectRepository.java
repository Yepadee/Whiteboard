package org.microboard.whiteboard.repositories.project;

import java.util.List;

import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseProjectRepository<T extends Project> extends CrudRepository<T, Long> {
	List<T> findByCreator(UnitDirector creator);
}