package org.microboard.whiteboard.services;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

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
		mockUnit.setId((long)1);
		mockUnit.setUnitName("Test Unit");
		
		unitService.addUnit(mockUnit);
		given(unitRepository.findById((long)1)).willReturn(Optional.ofNullable(mockUnit));
		
		Unit unit = unitService.getUnit((long)1);
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
		mockUnit.setId((long)2);
		mockUnit.setUnitName("Test Unit");
		
		given(unitRepository.findById((long)2)).willReturn(Optional.ofNullable(mockUnit));

		Unit unit = unitService.getUnit((long)2);
		assertThat(unit).isEqualTo(mockUnit);
	}
	
	@Test
	public void getAllUnits_returnsUnits() {
		Unit mockUnit1 = new Unit();
		mockUnit1.setId((long)1);
		Unit mockUnit2 = new Unit();
		mockUnit2.setId((long)2);
		Unit mockUnit3 = new Unit();
		mockUnit3.setId((long)3);
		
		given(unitRepository.findById((long)1)).willReturn(Optional.ofNullable(mockUnit1));
		given(unitRepository.findById((long)2)).willReturn(Optional.ofNullable(mockUnit2));
		given(unitRepository.findById((long)3)).willReturn(Optional.ofNullable(mockUnit3));
		
		List<Unit> units = unitService.getAllUnits();
		//getallunits returns empty list; is the repository connected properly?
		assertThat(units.size()).isEqualTo(3);
		assertThat(units.get(0)).isEqualTo(mockUnit1);
	}
}