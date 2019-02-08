package org.microboard.whiteboard.repositories.assessment;

import org.microboard.whiteboard.model.assessment.Assessment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseAssessmentRepository<T extends Assessment> extends CrudRepository<T, Long> {
}