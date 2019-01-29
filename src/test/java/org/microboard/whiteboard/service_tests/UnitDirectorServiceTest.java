package org.microboard.whiteboard.service_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.repositories.user.UnitDirectorRepository;
import org.microboard.whiteboard.services.user.UnitDirectorService;
import org.mockito.Mock;

public class UnitDirectorServiceTest {

	@Mock
	private UnitDirectorRepository repository;
	
	private UnitDirectorService service;
	
	@Before
	public void setUp() {
		this.service = new UnitDirectorService(repository);
	}
	
	@Test
	public void getUser_returnsUser() {
		UnitDirector mockUnitDirector = new UnitDirector();
		mockUnitDirector.setUserName("admin");
		
		given(repository.findByUserName("admin")).willReturn(Optional.ofNullable(mockUnitDirector));
		
		Optional<UnitDirector> maybeUnitDirector = service.getByUserName("admin");
		
		assertTrue(maybeUnitDirector.isPresent());
		assertThat(maybeUnitDirector.get().getUserName()).isEqualTo("admin");
	}

}
