package org.microboard.whiteboard.services.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.feedback.visitors.FeedbackUploadPathGen;
import org.microboard.whiteboard.model.task.Task;
import org.microboard.whiteboard.model.task.visitors.TaskUploadPathGen;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.repositories.task.FeedbackRepository;
import org.microboard.whiteboard.repositories.task.TaskRepository;
import org.microboard.whiteboard.services.user.AssessorService;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	public void submitFiles(long id, MultipartFile[] files, String comments) throws IOException {
		Task task = getTask(id);
		Assessor assessor = assessorService.getLoggedInUser();
		Feedback feedback = task.getIndividualFeedback(assessor);
		FeedbackUploadPathGen pathGen = new FeedbackUploadPathGen();
		feedback.accept(pathGen);
		String path = pathGen.getResult();
		new File(path).mkdir();
		
		for (MultipartFile file : files) {
			if (!file.isEmpty() && !file.equals(null)) {
				Path fileNameAndPath = Paths.get(path,file.getOriginalFilename());
				Files.write(fileNameAndPath, file.getBytes());
				feedback.addFile(path + file.getOriginalFilename());
			}
		
		}
		
		feedback.setTxtFeedback(comments);
		updateTask(feedback);
	}
	
	public void updateTask(Feedback feedback) {
		feedbackRepository.save(feedback);
	}
}
