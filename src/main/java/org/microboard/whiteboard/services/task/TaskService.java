package org.microboard.whiteboard.services.task;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.log.TaskAction;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskUploadPathGen;
import org.microboard.whiteboard.repositories.task.TaskRepository;
import org.microboard.whiteboard.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserService userService;
	
	public Task getTask(long id) throws RuntimeException{
		Optional<Task> maybeTask = taskRepository.findById(id);
		if (maybeTask.isPresent()) {
			return maybeTask.get();
		} else {
			throw new RuntimeException("No task exists with id \'" + id + "\'." );
		}
		
	}
	
	public void submitFiles(long id, MultipartFile[] files, String comments) throws IOException {
		//Look into making a mock file to test this
		Task task = getTask(id);
		task.setStatus("completed");
		TaskUploadPathGen pathGen = new TaskUploadPathGen();
		task.accept(pathGen);
		String path = pathGen.getResult();
		new File(path).mkdir();
		
		for (MultipartFile file : files) {
			if (!file.isEmpty() && !file.equals(null)) {
				if (file.getSize() < 524288000) {
					Path fileNameAndPath = Paths.get(path,file.getOriginalFilename());
					Files.write(fileNameAndPath, file.getBytes());
					task.addFile(path + file.getOriginalFilename());
					task.addAction(new TaskAction(userService.getLoggedInUser(), "submitted " + file.getOriginalFilename()));
				}
				else {
					System.out.println("File size exceeded for file " + path + file.getOriginalFilename());
					System.out.println("This file has size " + file.getSize());
				}
			}
		}
		task.setTxtSubmission(comments);
		task.addAction(new TaskAction(userService.getLoggedInUser(), "changed comments to \"" + comments + "\""));
		updateTask(task);
	}
	
	public void deleteFile(long id, String filename) {
		Task task = getTask(id);
		List<String> filePaths = task.getFileNames();
		for (String filePath : filePaths) {
			String f = filePath.substring(filePath.lastIndexOf("/")+1);
			if (f.equals(filename)) {
				File file = new File(filePath);
				file.delete();
				task.removeFile(filePath);
				task.addAction(new TaskAction(userService.getLoggedInUser(), "deleted " + filename));
				updateTask(task);
				break;
			}
		}
	}
	
	public ResponseEntity<Resource> downloadFile(long id, String filename) {
		Task task = getTask(id);
		List<String> filePaths = task.getFileNames();
		for (String filepath : filePaths) {
			String filepathWithoutPath = filepath.substring(filepath.lastIndexOf("/")+1);
			if (filepathWithoutPath.equals(filename)) {
				try {
					Path path = Paths.get(filepath);
					Resource resource = new UrlResource(path.toUri());
					return ResponseEntity.ok()
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
							.body(resource);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public void updateTask(Task task) {
		taskRepository.save(task);
	}
}
