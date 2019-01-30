package org.microboard.whiteboard.repositories.user;

import java.util.Optional;

import org.microboard.whiteboard.model.user.Unit;
import org.springframework.data.repository.CrudRepository;

public interface UnitRepository extends CrudRepository<Unit, Long>{
	Optional<Unit> findByUnitCode(String unitCode);	
}
