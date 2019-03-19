package org.microboard.whiteboard.services.task;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.dto.task.FileDto;
import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.feedback.visitors.FeedbackUploadPathGen;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskUploadPathGen;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.repositories.task.FeedbackRepository;
import org.microboard.whiteboard.repositories.task.TaskRepository;
import org.microboard.whiteboard.services.user.AssessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FeedbackService {
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private AssessorService assessorService;
	
	public Task getTask(long id) throws RuntimeException{
		Optional<Task> maybeTask = taskRepository.findById(id);
		if (maybeTask.isPresent()) {
			return maybeTask.get();
		} else {
			throw new RuntimeException("No task exists with id \'" + id + "\'." );
		}
	}
	
	public List<FileDto> createFileInfoInstance(Feedback feedback) {
		List<FileDto> fileinfo = new ArrayList<>();
		for (String filepath : feedback.getFileNames()) {
			FileDto f = new FileDto();
			f.setFileName(filepath.substring(filepath.lastIndexOf("/")+1));
			File file = new File(filepath);
			f.setFileSize(Long.toString(file.length()/1024) + "KB");
			f.setFilePath(filepath);
			fileinfo.add(f);	
		}
		return fileinfo;
	}
	
	public void submitFiles(long id, MultipartFile[] files, String comments, Integer marks) throws IOException {
		Task task = getTask(id);
		Assessor assessor = assessorService.getLoggedInUser();
		Feedback feedback = task.getIndividualFeedback(assessor);
		FeedbackUploadPathGen pathGen = new FeedbackUploadPathGen();
		feedback.accept(pathGen);
		String path = pathGen.getResult();
		new File(path).mkdir();
		
		for (MultipartFile file : files) {
			if (!file.isEmpty() && !file.equals(null)) {
				if (file.getSize() < 524288000) {
					Path fileNameAndPath = Paths.get(path,file.getOriginalFilename());
					Files.write(fileNameAndPath, file.getBytes());
					feedback.addFile(path + file.getOriginalFilename());
				}
				else {
					System.out.println("File size exceeded for file " + path + file.getOriginalFilename());
					System.out.println("This file has size " + file.getSize());
				}
			}
		}
		
		feedback.setStatus("marked");
		feedback.setTxtFeedback(comments);
		feedback.setMarks(marks);
		updateFeedback(feedback);
	}
	
	public void deleteFile(Feedback feedback, String filename) {
		List<String> filePaths = feedback.getFileNames();
		for (String filePath : filePaths) {
			String f = filePath.substring(filePath.lastIndexOf("/")+1);
			if (f.equals(filename)) {
				File file = new File(filePath);
				file.delete();
				feedback.deleteFile(filePath);
				updateFeedback(feedback);
				break;
			}
		}
	}
	
	public ResponseEntity<Resource> downloadFile(Feedback feedback, String filename) {
		List<String> filePaths = feedback.getFileNames();
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
	
	public void updateFeedback(Feedback feedback) {
		feedbackRepository.save(feedback);
	}
}
