package org.microboard.whiteboard.services.task;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.model.feedback.Feedback;
import org.microboard.whiteboard.model.feedback.FeedbackUploadPathGen;
import org.microboard.whiteboard.repositories.task.FeedbackRepository;
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
	
	public Feedback getFeedback(long id) throws RuntimeException{
		Optional<Feedback> maybeFeedback = feedbackRepository.findById(id);
		if (maybeFeedback.isPresent()) {
			return maybeFeedback.get();
		} else {
			throw new RuntimeException("No feedback exists with id \'" + id + "\'." );
		}
	}
	
	public void submitFiles(Long id, MultipartFile[] files, String comments, Integer marks, Boolean visable) throws IOException {
		Feedback feedback = getFeedback(id);
		FeedbackUploadPathGen pathGen = new FeedbackUploadPathGen();
		String path = pathGen.getFeedbackPath(feedback);
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
		
		feedback.setStatus("completed");
		feedback.setTxtFeedback(comments);
		feedback.setMarks(marks);
		feedback.setVisible(visable);
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
