package org.microboard.whiteboard.services;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.repositories.user.UserRepository;
import org.microboard.whiteboard.services.user.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	@Test
	public void getUserbyName_returnsUser() {
		User mockUser = new UnitDirector();
		mockUser.setUserName("admin");
		
		given(userRepository.findByUserName("admin")).willReturn(Optional.ofNullable(mockUser));
		
		User user = userService.getByUserName("admin");
		assertThat(user.getUserName()).isEqualTo("admin");
	}
	
	@Test
	public void getUser_returnCorrectType() {
		User mockUnitDirector = new UnitDirector();
		mockUnitDirector.setUserName("admin");
		User mockAssessor = new Assessor();
		mockAssessor.setUserName("assessor");
		User mockStudent = new Student();
		mockStudent.setUserName("student");
		
		given(userRepository.findByUserName("admin")).willReturn(Optional.ofNullable(mockUnitDirector));
		given(userRepository.findByUserName("assessor")).willReturn(Optional.ofNullable(mockAssessor));
		given(userRepository.findByUserName("student")).willReturn(Optional.ofNullable(mockStudent));

		User user = userService.getByUserName("admin");
		assertThat(user.getClass()).isEqualTo(UnitDirector.class);
		
		user = userService.getByUserName("assessor");
		assertThat(user.getClass()).isEqualTo(Assessor.class);
		
		user = userService.getByUserName("student");
		assertThat(user.getClass()).isEqualTo(Student.class);
	}

}
