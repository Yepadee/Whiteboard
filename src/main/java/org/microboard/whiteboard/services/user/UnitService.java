package org.microboard.whiteboard.services.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.repositories.user.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitService {
	
	@Autowired
	protected UnitRepository repository;
	
	public void addUnit(Unit unit) {
		repository.save(unit);
	}
	
	public Unit getUnit(Long id) throws RuntimeException{
		Optional<Unit> maybeUnit = repository.findById(id);
		if (maybeUnit.isPresent()) {
			return maybeUnit.get();
		} else {
			throw new RuntimeException("No unit found with id \'" + id + "\'.");
		}
		
	}
	
	public Optional<Unit> getByUnitCode(String unitCode) {
		return repository.findByUnitCode(unitCode);
	}
	
	public List<Unit> getAllUnits() {
		List<Unit> units = new ArrayList<>();
		repository.findAll().forEach(units::add);
		return units;
	}

	public void update(Unit unit) {
		repository.save(unit);
	}
	
}
