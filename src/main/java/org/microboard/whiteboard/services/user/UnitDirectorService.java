package org.microboard.whiteboard.services.user;

import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.repositories.user.UnitDirectorRepository;
import org.springframework.stereotype.Service;

@Service
public class UnitDirectorService extends BaseUserService<UnitDirector> {
	
	public UnitDirectorService(UnitDirectorRepository repository) {
		this.repository = repository;
	}
	
} 
