package org.microboard.whiteboard.services.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.repositories.user.AssessorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AssessorService {
	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private AssessorRepository assessorRepository;

	public AssessorService() {}
	
	public AssessorService(AssessorRepository assessorRepository) {
		this.assessorRepository = assessorRepository;
	}

	public void addUser(Assessor assessor) {
		assessorRepository.save(assessor);
		logger.info("Added user \"" + assessor.getUserName() + "\"");
	}

	public List<Assessor> getAllUsers() {
		List<Assessor> assessors = new ArrayList<>();
		assessorRepository.findAll().forEach(assessors::add);
		return assessors;
	}

	public Optional<Assessor> getAssessor(Long id) {
		return assessorRepository.findById(id);
	}

	public void DeleteAll() {
		assessorRepository.deleteAll();
	}

	public void updateAssessor(Assessor newUser) {
		assessorRepository.save(newUser);
	}

	public void deleteAssessor(Long id) {
		assessorRepository.deleteById(id);
	}

	public Optional<Assessor> getByUserName(String name) {
		return assessorRepository.findByUserName(name);
	}
}
