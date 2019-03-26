package org.microboard.whiteboard.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.dto.assessment.AssessmentDto;
import org.microboard.whiteboard.dto.assessment.GroupAssessmentDto;
import org.microboard.whiteboard.dto.assessment.SoloAssessmentDto;
import org.microboard.whiteboard.dto.project.GroupProjectDto;
import org.microboard.whiteboard.dto.project.ProjectDto;
import org.microboard.whiteboard.dto.project.SoloProjectDto;
import org.microboard.whiteboard.dto.user.MarkerGroupDto;
import org.microboard.whiteboard.dto.user.MarkerUserDto;
import org.microboard.whiteboard.model.assessment.Assessment;
import org.microboard.whiteboard.model.assessment.GroupAssessment;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.GroupTask;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.model.user.UnitDirector;

public class ProjectTemplateMaker {
	private void fillCoreProjectTemplate(Project project, ProjectDto<?> template) {
		long id = project.getId();
		String name = project.getName();
		String description = project.getDescription();
		Unit unit = project.getUnit();
		List<UnitDirector> helpers = project.getHelpers();
		
		template.setId(id);
		template.setName(name);
		template.setDescription(description);
		template.setUnit(unit);
		template.setHelpers(helpers);
	}
	
	private void fillCoreAssessmentTemplate(Assessment assessment, AssessmentDto template) {
		Long assessmentId = assessment.getId();
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
	
	public SoloProjectDto getTemplate(SoloProject soloProject) {
		SoloProjectDto template = new SoloProjectDto();
		fillCoreProjectTemplate(soloProject, template);
		
		for (SoloAssessment soloAssessment : soloProject.getAssessments()) {
			SoloAssessmentDto assessmentTemplate = new SoloAssessmentDto();
			fillCoreAssessmentTemplate(soloAssessment, assessmentTemplate);
			
			List<MarkerUserDto> markerDtos = new ArrayList<>();
			for (SoloTask task : soloAssessment.getTasks()) {
				
				for (Assessor marker : task.getFeedback().keySet()) {
					MarkerUserDto markerDto;
					Optional<MarkerUserDto> maybeMarkerDto = findSoloMarker(markerDtos, marker);
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
			assessmentTemplate.setSoloMarkerDtos(markerDtos);
			template.getAssessments().add(assessmentTemplate);
		}
		
		return template;
	}
		
	private Optional<MarkerUserDto> findSoloMarker(final List<MarkerUserDto> markerDtos, final Assessor marker){
		return markerDtos.stream().filter(mDto -> mDto.getMarker().equals(marker)).findFirst();
	}
	
	public GroupProjectDto getTemplate(GroupProject groupProject) {
		GroupProjectDto template = new GroupProjectDto();
		
		template.setGroups(groupProject.getGroups());
		
		fillCoreProjectTemplate(groupProject, template);
		
		for (GroupAssessment soloAssessment : groupProject.getAssessments()) {
			GroupAssessmentDto assessmentTemplate = new GroupAssessmentDto();
			fillCoreAssessmentTemplate(soloAssessment, assessmentTemplate);
			List<MarkerGroupDto> markerDtos = new ArrayList<>();
			for (GroupTask task : soloAssessment.getTasks()) {
				
				for (Assessor marker : task.getFeedback().keySet()) {
					MarkerGroupDto markerDto;
					Optional<MarkerGroupDto> maybeMarkerDto = findGroupMarker(markerDtos, marker);
					if (maybeMarkerDto.isPresent()) {
						markerDto = maybeMarkerDto.get();
					} else {
						markerDto = new MarkerGroupDto();
						markerDto.setMarker(marker);
						markerDtos.add(markerDto);
					}
					markerDto.getToMark().add(task.getAccountable());
				}
			}
			assessmentTemplate.setGroupMarkerDtos(markerDtos);
			template.getAssessments().add(assessmentTemplate);
		}
		
		return template;
	}
		
	private Optional<MarkerGroupDto> findGroupMarker(final List<MarkerGroupDto> markerDtos, final Assessor marker){
		return markerDtos.stream().filter(mDto -> mDto.getMarker().equals(marker)).findFirst();
	}
}
