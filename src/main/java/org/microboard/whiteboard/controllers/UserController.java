package org.microboard.whiteboard.controllers;

import java.util.Optional;

import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("/tasks")
	public String getSubmissionPage() {
		Optional<Task> task = taskService.getTask(1);
		return "error";
	}
	
}

