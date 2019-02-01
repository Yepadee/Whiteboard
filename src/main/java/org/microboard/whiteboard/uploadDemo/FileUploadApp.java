package org.microboard.whiteboard.uploadDemo;

import java.io.File;

import org.microboard.whiteboard.WhiteboardSpringApplication;
import org.microboard.whiteboard.controllers.UserController;
import org.microboard.whiteboard.model.user.User;
import org.springframework.boot.SpringApplication;
//import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
//@ComponentScan({"org.microboard.whiteboard.uploadDemo","controller"})
public class FileUploadApp {
	
	String uploadDir = System.getProperty("user.dir")+"/uploads";	
	
	public void addBaseUploadDirectory() {
		new File(uploadDir).mkdir();
	}
}
