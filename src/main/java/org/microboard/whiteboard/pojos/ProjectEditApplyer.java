package org.microboard.whiteboard.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.microboard.whiteboard.dto.assessment.SoloAssessmentDto;
import org.microboard.whiteboard.dto.project.ProjectDto;
import org.microboard.whiteboard.dto.project.SoloProjectDto;
import org.microboard.whiteboard.dto.user.MarkerUserDto;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.project.Project;
import org.microboard.whiteboard.model.project.SoloProject;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.Unit;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;

public class ProjectEditApplyer {
	
	private void applyCoreProjectEdits(Project project, ProjectDto edits) {
		Long id = project.getId();
		String name = edits.getName();
		String description = edits.getDescription();
		List<UnitDirector> helpers = new ArrayList<>(); //TODO
		Unit unit = edits.getUnit();
		
		project.setId(id);
		project.setName(name);
		project.setDescription(description);
		project.setHelpers(helpers);
		project.setUnit(unit);
	}
	
	private void applyCoreAssessmentEdits(SoloAssessment assessment, SoloAssessmentDto edits) {
		String name = edits.getName();
		String description = edits.getDescription();
		Date studentDeadline = edits.getStudentDeadline();
		Date markerDeadline = edits.getMarkerDeadline();
		int weight = edits.getWeight();
		
		assessment.setName(name);
		assessment.setDescription(description);
		assessment.setStudentDeadline(studentDeadline);
		assessment.setMarkerDeadline(markerDeadline);
		assessment.setStudentDeadline(studentDeadline);
		assessment.setWeight(weight);
	}
	
	public SoloProject applyEdits(SoloProject project, SoloProjectDto edits) {
		List<SoloAssessment> oldAssessments = new ArrayList<>(project.getAssessments());
		List<SoloAssessment> presentAssessments = new ArrayList<>();
		
		boolean newUnit = false;
		if (project.getUnit() != null) {
			newUnit = project.getUnit().getId() != edits.getUnit().getId();
		}
		
		applyCoreProjectEdits(project, edits);
		for (SoloAssessmentDto editAssessment : edits.getAssessments()) {
			Long assessmentId = editAssessment.getId();
			if (assessmentId != null) {
				//Editing an existing assessment
				Optional<SoloAssessment> maybeAssessment = findById(oldAssessments, assessmentId);
				
				if (maybeAssessment.isPresent()) {
					SoloAssessment assessment = findById(oldAssessments, assessmentId).get();
					//If new unit, remove all tasks and add new tasks for members of the new cohort.
					if (newUnit) {
						assessment.getTasks().removeAll(assessment.getTasks());
						for (User user : project.getUnit().getCohort()) {
							SoloTask newTask = new SoloTask();
							user.addTask(newTask);
							assessment.addTask(newTask);
							
						}
					}
					//Add markers to each task
					for (SoloTask soloTask : assessment.getTasks()) {
						soloTask.setMarkers(new ArrayList<>());
						User accountable = soloTask.getAccountable();
						for (MarkerUserDto markerDto : editAssessment.getMarkerDtos()) {
							Assessor marker = markerDto.getMarker();
							if (markerDto.getToMark().contains(accountable)) {
								soloTask.addMarker(marker);
							}
						}
					}
					presentAssessments.add(assessment);
					applyCoreAssessmentEdits(assessment, editAssessment);
				} else {
					//Adding a new assessment
					SoloAssessment assessment = new SoloAssessment();
					for (User user : project.getUnit().getCohort()) {
						SoloTask soloTask = new SoloTask();
						assessment.addTask(soloTask);
						
						for (MarkerUserDto markerDto : editAssessment.getMarkerDtos()) {
							Assessor marker = markerDto.getMarker();
							if (markerDto.getToMark().contains(user)) {
								soloTask.addMarker(marker);
							}
						}
						soloTask.setAccountable(user);
					}
					project.addAssessment(assessment);
					applyCoreAssessmentEdits(assessment, editAssessment);
				}
				
			} else {
				//Adding a new assessment
				SoloAssessment assessment = new SoloAssessment();
				for (User user : project.getUnit().getCohort()) {
					SoloTask soloTask = new SoloTask();
					assessment.addTask(soloTask);
					
					for (MarkerUserDto markerDto : editAssessment.getMarkerDtos()) {
						Assessor marker = markerDto.getMarker();
						if (markerDto.getToMark().contains(user)) {
							soloTask.addMarker(marker);
						}
					}
					soloTask.setAccountable(user);
				}
				project.addAssessment(assessment);
				applyCoreAssessmentEdits(assessment, editAssessment);
			}
		}
		
		List<SoloAssessment> removed = new ArrayList<>(oldAssessments);
		removed.removeAll(presentAssessments);
		project.getAssessments().removeAll(removed);
		
		return project;
	}
	
	private Optional<SoloAssessment> findById(List<SoloAssessment> soloAssessments, Long id) {
		return soloAssessments.stream().filter(a -> a.getId() == id).findFirst();
	}
}
