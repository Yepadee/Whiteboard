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
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;

public class ProjectEditApplyer {
	
	private void applyCoreProjectEdits(Project project, NewProject edits) {
		long id = project.getId();
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
	
	private void applyCoreAssessmentEdits(SoloAssessment assessment, NewSoloAssessment edits) {
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
	
	public SoloProject applyEdits(SoloProject project, NewSoloProject edits) {
		applyCoreProjectEdits(project, edits);
		
		List<SoloAssessment> oldAssessments = new ArrayList<>(project.getAssessments());
		List<SoloAssessment> presentAssessments = new ArrayList<>();
		
		
		for (NewSoloAssessment editAssessment : edits.getAssessments()) {
			long assessmentId = editAssessment.getId();
			SoloAssessment assessment = new SoloAssessment();
			Optional<SoloAssessment> maybeAssessment = findById(oldAssessments, assessmentId);
			if (maybeAssessment.isPresent()) {
				//Editing an existing assessment
				assessment = maybeAssessment.get();
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
			} else {
				//Adding a new assessment
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
			}
			
			applyCoreAssessmentEdits(assessment, editAssessment);
		}
		
		List<SoloAssessment> removed = new ArrayList<>(oldAssessments);
		removed.removeAll(presentAssessments);
		project.getAssessments().removeAll(removed);
		
		return project;
	}
	
	private Optional<SoloAssessment> findById(List<SoloAssessment> soloAssessments, long id) {
		return soloAssessments.stream().filter(a -> a.getId() == id).findFirst();
	}
}
