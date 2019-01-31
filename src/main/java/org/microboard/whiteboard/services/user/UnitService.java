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
	
	public Optional<Unit> getUnit(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Unit> getByUnitCode(String unitCode) {
		return repository.findByUnitCode(unitCode);
	}
	
	public List<Unit> getAllUnits() {
		List<Unit> units = new ArrayList<>();
		repository.findAll().forEach(units::add);
		return units;
	}
	
}
