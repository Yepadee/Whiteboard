package org.microboard.whiteboard.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.dto.assessment.NewSoloAssessment;
import org.microboard.whiteboard.dto.project.NewProject;
import org.microboard.whiteboard.dto.project.NewSoloProject;
import org.microboard.whiteboard.dto.user.MarkerUserDto;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Unit;

public class ProjectTemplateMaker {
	public void fillCoreTemplate(Project project, NewProject template) {
		long id = project.getId();
		String name = project.getName();
		String description = project.getDescription();
		Unit unit = project.getUnit();
		
		template.setId(id);
		template.setName(name);
		template.setDescription(description);
		template.setUnit(unit);
	}
	
	public NewSoloProject getTemplate(SoloProject soloProject) {
		NewSoloProject template = new NewSoloProject();
		fillCoreTemplate(soloProject, template);
		
		for (SoloAssessment soloAssessment : soloProject.getAssessments()) {
			long assessmentId = soloAssessment.getId();
			String assessmentName = soloAssessment.getName();
			String assessmentDesc = soloAssessment.getDescription();
			Date studentDeadline = soloAssessment.getStudentDeadline();
			Date markerDeadline = soloAssessment.getMarkerDeadline();
			int weight = soloAssessment.getWeight();
			
			NewSoloAssessment editAssessment = new NewSoloAssessment();
			editAssessment.setId(assessmentId);
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
			template.getAssessments().add(editAssessment);
		}
		
		return template;
	}
		
	private Optional<MarkerUserDto> findMarker(final List<MarkerUserDto> markerDtos, final Assessor marker){
		return markerDtos.stream().filter(mDto -> mDto.getMarker().equals(marker)).findFirst();
	}
}
