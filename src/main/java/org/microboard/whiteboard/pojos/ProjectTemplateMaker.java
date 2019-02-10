package org.microboard.whiteboard.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.dto.assessment.NewAssessment;
import org.microboard.whiteboard.dto.assessment.NewSoloAssessment;
import org.microboard.whiteboard.dto.project.NewProject;
import org.microboard.whiteboard.dto.project.NewSoloProject;
import org.microboard.whiteboard.dto.user.MarkerUserDto;
import org.microboard.whiteboard.model.assessment.Assessment;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Unit;

public class ProjectTemplateMaker {
	private void fillCoreProjectTemplate(Project project, NewProject template) {
		long id = project.getId();
		String name = project.getName();
		String description = project.getDescription();
		Unit unit = project.getUnit();
		
		template.setId(id);
		template.setName(name);
		template.setDescription(description);
		template.setUnit(unit);
	}
	
	private void fillCoreAssessmentTemplate(Assessment assessment, NewAssessment template) {
		long assessmentId = assessment.getId();
		String assessmentName = assessment.getName();
		String assessmentDesc = assessment.getDescription();
		Date studentDeadline = assessment.getStudentDeadline();
		Date markerDeadline = assessment.getMarkerDeadline();
		int weight = assessment.getWeight();
		
		template.setId(assessmentId);
		template.setName(assessmentName);
		template.setDescription(assessmentDesc);
		template.setMarkerDeadline(markerDeadline);
		template.setStudentDeadline(studentDeadline);
		template.setWeight(weight);
	}
	
	public NewSoloProject getTemplate(SoloProject soloProject) {
		NewSoloProject template = new NewSoloProject();
		fillCoreProjectTemplate(soloProject, template);
		
		for (SoloAssessment soloAssessment : soloProject.getAssessments()) {
			NewSoloAssessment assessmentTemplate = new NewSoloAssessment();
			fillCoreAssessmentTemplate(soloAssessment, assessmentTemplate);
			
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
			assessmentTemplate.setMarkerDtos(markerDtos);
			template.getAssessments().add(assessmentTemplate);
		}
		
		return template;
	}
		
	private Optional<MarkerUserDto> findMarker(final List<MarkerUserDto> markerDtos, final Assessor marker){
		return markerDtos.stream().filter(mDto -> mDto.getMarker().equals(marker)).findFirst();
	}
}
