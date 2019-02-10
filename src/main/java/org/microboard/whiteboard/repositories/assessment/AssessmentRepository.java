package org.microboard.whiteboard.repositories.assessment;

import org.microboard.whiteboard.model.assessment.Assessment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AssessmentRepository extends BaseAssessmentRepository<Assessment> {
}