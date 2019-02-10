package org.microboard.whiteboard.services.assessment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.assessment.Assessment;
import org.microboard.whiteboard.repositories.assessment.BaseAssessmentRepository;
import org.microboard.whiteboard.services.user.BaseUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseAssessmentService<T extends Assessment> {
	
	Logger logger = LoggerFactory.getLogger(BaseUserService.class);

	@Autowired
	protected BaseAssessmentRepository<T> repository;

	public void addAssessment(T assessment) {
		repository.save(assessment);
		logger.info("Added user \"" + assessment.getName() + "\"");
	}

	public List<T> getAllAssessments() {
		List<T> assessments = new ArrayList<>();
		repository.findAll().forEach(assessments::add);

		return assessments;
	}

	public Optional<T> getAssessment(Long id) {
		return repository.findById(id);
	}

	public void DeleteAll() {
		repository.deleteAll();
	}

	public void updateAssessment(T newAssessment) {
		repository.save(newAssessment);
	}

	public void deleteAssessment(Long id) {
		repository.deleteById(id);
	}
}