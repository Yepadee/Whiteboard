package org.microboard.whiteboard.controller_tests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.microboard.whiteboard.controllers.LoginController;
import org.microboard.whiteboard.repositories.UserRepository;
import org.microboard.whiteboard.services.ProjectService;
import org.microboard.whiteboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private ProjectService projectService;
	
	@Test
	public void test() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/login"))
		.andExpect(status().isOk());
	}

}
