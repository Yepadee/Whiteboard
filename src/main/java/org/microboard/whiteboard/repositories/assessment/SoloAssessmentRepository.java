package org.microboard.whiteboard.repositories.assessment;

import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SoloAssessmentRepository extends BaseAssessmentRepository<SoloAssessment> {
}