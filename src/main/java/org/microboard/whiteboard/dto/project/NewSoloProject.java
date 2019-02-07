package org.microboard.whiteboard.dto.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.dto.assessment.NewSoloAssessment;
import org.microboard.whiteboard.dto.user.MarkerUserDto;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Assessor;

public class NewSoloProject extends NewProject {
	private List<NewSoloAssessment> assessments = new ArrayList<>();
	
	public NewSoloProject() {}
	
	public NewSoloProject(SoloProject project) {
		super(project);
		
		for (SoloAssessment soloAssessment : project.getAssessments()) {
			String assessmentName = soloAssessment.getName();
			String assessmentDesc = soloAssessment.getDescription();
			Date studentDeadline = soloAssessment.getStudentDeadline();
			Date markerDeadline = soloAssessment.getMarkerDeadline();
			int weight = soloAssessment.getWeight();
			
			NewSoloAssessment editAssessment = new NewSoloAssessment();
			editAssessment.setName(assessmentName);
			editAssessment.setDescription(assessmentDesc);
			editAssessment.setMarkerDeadline(markerDeadline);
			editAssessment.setStudentDeadline(studentDeadline);
			editAssessment.setWeight(weight);
			
			
			List<MarkerUserDto> markerDtos = new ArrayList<>();
			for (SoloTask task : soloAssessment.getTasks()) {
				
				for (Assessor marker : task.getMarkers()) {
					MarkerUserDto markerDto;
					Optional<MarkerUserDto> maybeMarkerDto = findMarker(markerDtos, marker);
					if (maybeMarkerDto.isPresent()) {
						markerDto = maybeMarkerDto.get();
					} else {
						markerDto = new MarkerUserDto();
						markerDto.setMarker(marker);
						markerDtos.add(markerDto);
					}
					markerDto.getToMark().add(task.getAccountable());
				}
			}
			editAssessment.setMarkerDtos(markerDtos);
			assessments.add(editAssessment);
		}
	}
	
	private Optional<MarkerUserDto> findMarker(final List<MarkerUserDto> markerDtos, final Assessor marker){
	    return markerDtos.stream().filter(mDto -> mDto.getMarker().equals(marker)).findFirst();
	}
	
	public List<NewSoloAssessment> getAssessments() {
		return assessments;
	}
	public void setAssessments(List<NewSoloAssessment> assessments) {
		this.assessments = assessments;
	}
	
	protected boolean validateAssessments() {
		boolean valid = true;
		int i = 1;
		for (NewSoloAssessment assessment : assessments) {
			valid = valid && assessment.validate();
			errorMsg += "Assessment " + i + ": {" + assessment.getErrorMsg() + "}";
			i ++;
		}
		
		return valid;
	}
}
