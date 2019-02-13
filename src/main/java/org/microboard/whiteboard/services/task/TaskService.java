package org.microboard.whiteboard.services.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.dto.task.FileDto;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskUploadPathGen;
import org.microboard.whiteboard.repositories.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	
	public Task getTask(long id) throws RuntimeException{
		Optional<Task> maybeTask = taskRepository.findById(id);
		if (maybeTask.isPresent()) {
			return maybeTask.get();
		} else {
			throw new RuntimeException("No task exists with id \'" + id + "\'." );
		}
		
	}
	
	public List<FileDto> createFileInfoInstance(Task task) {
		List<FileDto> fileinfo = new ArrayList<>();
		for (String filepath : task.getFileNames()) {
			FileDto f = new FileDto();
			f.setFileName(filepath.substring(filepath.lastIndexOf("/")+1));
			File file = new File(filepath);
			f.setFileSize(Long.toString(file.length()/1024) + "KB");
			f.setFilePath(filepath);
			fileinfo.add(f);
			
		}
		return fileinfo;
	}
	
	public void submitFiles(long id, MultipartFile[] files, String comments) throws IOException {
		Task task = getTask(id);
			
		TaskUploadPathGen pathGen = new TaskUploadPathGen();
		task.accept(pathGen);
		String path = pathGen.getResult();
		new File(path).mkdir();
		
		for (MultipartFile file : files) {
			if (!file.isEmpty() && !file.equals(null)) {
				Path fileNameAndPath = Paths.get(path,file.getOriginalFilename());
				Files.write(fileNameAndPath, file.getBytes());
				task.addFile(path + file.getOriginalFilename());
			}
		
		}
		
		task.setTxtSubmission(comments);
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
				updateTask(task);
				break;
			}
		}
	}
	
	public void updateTask(Task task) {
		taskRepository.save(task);
	}
}
