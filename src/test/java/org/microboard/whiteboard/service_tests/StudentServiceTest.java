package org.microboard.whiteboard.service_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.repositories.user.AssessorRepository;
import org.microboard.whiteboard.repositories.user.StudentRepository;
import org.microboard.whiteboard.services.user.AssessorService;
import org.microboard.whiteboard.services.user.StudentService;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

	@Mock
	private StudentRepository studentRepository;
	
	private StudentService studentService;
	
	@Before
	public void setUp() {
		this.studentService = new StudentService(studentRepository);
	}
	
	@Test
	public void getUser_returnsStudent() {
		Student mockStudent = new Student();
		mockStudent.setUserName("student");
		
		given(studentRepository.findByUserName("student")).willReturn(Optional.ofNullable(mockStudent));
		
		Optional<Student> maybeStudent = studentService.getByUserName("student");
		
		assertTrue(maybeStudent.isPresent());
		assertThat(maybeStudent.get().getUserName()).isEqualTo("student");
	}

}