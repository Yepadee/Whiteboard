package org.microboard.whiteboard.services;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;
import org.microboard.whiteboard.repositories.user.UserRepository;
import org.microboard.whiteboard.services.user.UserService;
import org.microboard.whiteboard.services.user.AssessorService;
import org.microboard.whiteboard.services.user.BaseUserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	@InjectMocks
	private AssessorService assessorService;
	//@InjectMocks
	//private BaseUserService baseUserService;
	
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
	
	@Test
	public void changePerms_setsAsAssessor() {
		User mockStudent = new Student();
		mockStudent.setUserName("Student");
		User mockUnitDirector = new UnitDirector();
		mockUnitDirector.setUserName("UnitDirector");
		
		given(userRepository.findByUserName("Student")).willReturn(Optional.ofNullable(mockStudent));
		given(userRepository.findByUserName("UnitDirector")).willReturn(Optional.ofNullable(mockUnitDirector));
		
		assessorService.changePerms(mockStudent);
		assessorService.changePerms(mockUnitDirector);
		
		User student = assessorService.getByUserName("Student");
		User unitdirector = assessorService.getByUserName("UnitDirector");

		assertThat(student.getClass()).isEqualTo(Assessor.class);
		assertThat(unitdirector.getClass()).isEqualTo(Assessor.class);
		//is the assessor repository needed to be referenced?
	}

	@Test
	public void addUser_addsUser() {
		User mockUser = new Student();
		mockUser.setUserName("student");
		userService.addUser(mockUser);
		given(userRepository.findByUserName("student")).willReturn(Optional.ofNullable(mockUser));
		User student = userService.getByUserName("student");
		assertThat(student).isEqualTo(mockUser);
	}
	
	@Test
	public void getAllUsers_returnsUser() {
		
		User mockStudent1 = new Student();
		mockStudent1.setUserName("student1");
		User mockStudent2 = new Student();
		mockStudent2.setUserName("student2");
		User mockStudent3 = new Student();
		mockStudent3.setUserName("student3");
			
		given(userRepository.findByUserName("student1")).willReturn(Optional.ofNullable(mockStudent1));
		given(userRepository.findByUserName("student2")).willReturn(Optional.ofNullable(mockStudent2));
		given(userRepository.findByUserName("student3")).willReturn(Optional.ofNullable(mockStudent3));
		
		List<User> mockUsers = userService.getAllUsers();
		//getallusers returns empty list; is the repository connected properly?
		assertThat(mockUsers.size()).isEqualTo(3);
		assertThat(mockUsers.get(0)).isEqualTo(mockStudent1);
		}

	@Test
	public void deleteUser_removesFromRepository() {
		User deleteUser = new Student();
		deleteUser.setUserName("delete");
		deleteUser.setId((long)10);
		userService.addUser(deleteUser);
		User remainUser = new Student();
		remainUser.setUserName("remain");
		remainUser.setId((long)2);
		userService.addUser(remainUser);
		
		given(userRepository.findByUserName("delete")).willReturn(Optional.ofNullable(deleteUser));
		given(userRepository.findByUserName("remain")).willReturn(Optional.ofNullable(remainUser));

		userService.deleteUser((long)1);

		Optional<User> deletedUser = userRepository.findByUserName("delete");
		//test fails; is the repository connected properly?
		assertThat(deletedUser.isPresent()).isEqualTo(false);
	}
	
	@Test
	public void updateUser_overwritesIfUserExists() {
		User mockUser = new Student();
		mockUser.setUserName("student1");
		userService.addUser(mockUser);
		mockUser.setUserName("student2");
		userService.updateUser(mockUser);
		
		given(userRepository.findByUserName("student2")).willReturn(Optional.ofNullable(mockUser));
		
		User student = userService.getByUserName("student2");
		assertThat(student).isEqualTo(mockUser);
	}
}

