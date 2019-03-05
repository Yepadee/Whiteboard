package org.microboard.whiteboard.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.repositories.user.AssessorRepository;
import org.microboard.whiteboard.services.user.AssessorService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AssessorServiceTest {

	@Mock
	private AssessorRepository assessorRepository;
	
	@InjectMocks
	private AssessorService assessorService;
	
	
	@Test
	public void getUser_returnsAssessor() {
		Assessor mockAssessor = new Assessor();
		mockAssessor.setUserName("assessor");
		
		given(assessorRepository.findByUserName("assessor")).willReturn(Optional.ofNullable(mockAssessor));
		
		Assessor assessor = assessorService.getByUserName("assessor");
		
		assertThat(assessor.getUserName()).isEqualTo("assessor");
	}

}