package org.microboard.whiteboard.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.microboard.whiteboard.model.user.Student;
import org.microboard.whiteboard.repositories.user.StudentRepository;
import org.microboard.whiteboard.services.user.StudentService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

	@Mock
	private StudentRepository studentRepository;
	
	@InjectMocks
	private StudentService studentService;
	
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