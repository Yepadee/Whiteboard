package org.microboard.whiteboard.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.repositories.user.UnitRepository;
import org.microboard.whiteboard.services.user.UnitService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UnitServiceTest {

	@Mock
	private UnitRepository unitRepository;
	
	@InjectMocks
	private UnitService unitService;

	@Test
	public void addUnit_addsUnit() {
		Unit mockUnit = new Unit();
		mockUnit.setId(1L);
		mockUnit.setUnitName("Test Unit");
		
		unitService.addUnit(mockUnit);
		given(unitRepository.findById(1L)).willReturn(Optional.ofNullable(mockUnit));
		
		Unit unit = unitService.getUnit(1L);
		assertThat(unit).isEqualTo(mockUnit);
	}
	
	@Test
	public void getByUnitCode_returnsUnit() {
		Unit mockUnit = new Unit();
		mockUnit.setUnitCode("TEST10001");
		mockUnit.setUnitName("Test Unit");
		
		given(unitRepository.findByUnitCode("TEST10001")).willReturn(Optional.ofNullable(mockUnit));

		Optional<Unit> maybeUnit = unitService.getByUnitCode("TEST10001");
		assertThat(maybeUnit.get()).isEqualTo(mockUnit);
	}
	
	@Test
	public void getUnit_returnsUnit() {
		Unit mockUnit = new Unit();
		mockUnit.setId(2L);
		mockUnit.setUnitName("Test Unit");
		
		given(unitRepository.findById(2L)).willReturn(Optional.ofNullable(mockUnit));

		Unit unit = unitService.getUnit(2L);
		assertThat(unit).isEqualTo(mockUnit);
	}
	
	@Test
	public void getAllUnits_returnsUnits() {
		Unit mockUnit1 = new Unit();
		mockUnit1.setId(1L);
		Unit mockUnit2 = new Unit();
		mockUnit2.setId(2L);
		Unit mockUnit3 = new Unit();
		mockUnit3.setId(3L);
		
		Set<Unit> mockUnits = new HashSet<>();
		mockUnits.add(mockUnit1);
		mockUnits.add(mockUnit2);
		mockUnits.add(mockUnit3);
		
		given(unitRepository.findAll()).willReturn(mockUnits);
		

		List<Unit> units = unitService.getAllUnits();
		assertThat(units.size()).isEqualTo(3);
	}
}