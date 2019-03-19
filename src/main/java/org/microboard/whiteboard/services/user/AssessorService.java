package org.microboard.whiteboard.services.user;

import java.util.List;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.repositories.user.AssessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessorService extends BaseUserService<Assessor>{
	@Autowired
	protected AssessorRepository repository;
	
	public List<User> getOnlyAssessors() {
		return repository.getOnlyAssessors();
	}
} 
