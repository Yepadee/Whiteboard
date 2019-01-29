package org.microboard.whiteboard.services.user;

import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.repositories.user.AssessorRepository;
import org.microboard.whiteboard.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AssessorService extends BaseUserService<Assessor> {
	
	public AssessorService(AssessorRepository repository) {
		this.repository = repository;
	}
	
} 
