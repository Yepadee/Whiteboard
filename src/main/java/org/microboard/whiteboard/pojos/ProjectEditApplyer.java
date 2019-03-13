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
import org.microboard.whiteboard.model.user.Group;
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
	
	private void applyCoreAssessmentEdits(Assessment assessment, AssessmentDto edits) {
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
	
	public void applyEdits(SoloProject project, SoloProjectDto edits) {
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
				Optional<SoloAssessment> maybeAssessment = findSoloAssessmentById(oldAssessments, assessmentId);
				
				if (maybeAssessment.isPresent()) {
					SoloAssessment assessment = findSoloAssessmentById(oldAssessments, assessmentId).get();
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
						List<Assessor> oldMarkers = soloTask.getMarkers();
						List<Assessor> presentMarkers = new ArrayList<>();
						
						User accountable = soloTask.getAccountable();
						for (MarkerUserDto markerDto : editAssessment.getSoloMarkerDtos()) {
							Assessor marker = markerDto.getMarker();
							
							if (markerDto.getToMark().contains(accountable) ) {
								if (! soloTask.getMarkers().contains(marker)) {
									soloTask.addMarker(marker);
								}
								presentMarkers.add(marker);
							}
						}
						List<Assessor> removedMarkers = new ArrayList<>(oldMarkers);
						removedMarkers.removeAll(presentMarkers);
						soloTask.getMarkers().removeAll(removedMarkers);
					}
					presentAssessments.add(assessment);
					applyCoreAssessmentEdits(assessment, editAssessment);
				} else {
					//Adding a new assessment
					SoloAssessment assessment = new SoloAssessment();
					for (User user : project.getUnit().getCohort()) {
						SoloTask soloTask = new SoloTask();
						assessment.addTask(soloTask);
						
						for (MarkerUserDto markerDto : editAssessment.getSoloMarkerDtos()) {
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
					
					for (MarkerUserDto markerDto : editAssessment.getSoloMarkerDtos()) {
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
	}
	
	private Optional<SoloAssessment> findSoloAssessmentById(List<SoloAssessment> soloAssessments, Long id) {
		return soloAssessments.stream().filter(a -> a.getId() == id).findFirst();
	}
	
	public void applyEdits(GroupProject project, GroupProjectDto edits) {
		boolean newUnit = false;
		if (project.getUnit() != null) {
			newUnit = project.getUnit().getId() != edits.getUnit().getId();
		}
		
		//If new unit, remove all groups.
		if (newUnit) {
			project.getGroups().removeAll(project.getGroups());
		}
		
		for (Group group : edits.getGroups()) {
			if (! project.getGroups().contains(group)) {
				group.setProject(project);
				project.getGroups().add(group);
			}
		}
		
		List<Group> toRemove = new ArrayList<>();
		
		for (Group group : project.getGroups()) {
			if (! edits.getGroups().contains(group)) {
				toRemove.add(group);
			}
		}
		
		project.getGroups().removeAll(toRemove);
		
		List<GroupAssessment> oldAssessments = new ArrayList<>(project.getAssessments());
		List<GroupAssessment> presentAssessments = new ArrayList<>();
		

		
		applyCoreProjectEdits(project, edits);
		for (GroupAssessmentDto editAssessment : edits.getAssessments()) {
			Long assessmentId = editAssessment.getId();
			if (assessmentId != null) {
				//Editing an existing assessment
				Optional<GroupAssessment> maybeAssessment = findByGroupAssessmentId(oldAssessments, assessmentId);
				
				if (maybeAssessment.isPresent()) {
					GroupAssessment assessment = findByGroupAssessmentId(oldAssessments, assessmentId).get();

					
					//Add markers to each task
					for (GroupTask groupTask : assessment.getTasks()) {
						List<Assessor> oldMarkers = groupTask.getMarkers();
						List<Assessor> presentMarkers = new ArrayList<>();
						
						Group accountable = groupTask.getAccountable();
						for (MarkerGroupDto markerDto : editAssessment.getGroupMarkerDtos()) {
							Assessor marker = markerDto.getMarker();
							
							if (markerDto.getToMark().contains(accountable) ) {
								if (! groupTask.getMarkers().contains(marker)) {
									groupTask.addMarker(marker);
								}
								presentMarkers.add(marker);
							}
						}
						List<Assessor> removedMarkers = new ArrayList<>(oldMarkers);
						removedMarkers.removeAll(presentMarkers);
						groupTask.getMarkers().removeAll(removedMarkers);
					}
					presentAssessments.add(assessment);
					applyCoreAssessmentEdits(assessment, editAssessment);
				} else {
					//Adding a new assessment
					GroupAssessment assessment = new GroupAssessment();
					for (Group group : project.getGroups()) {
						GroupTask groupTask = new GroupTask(group);
						assessment.addTask(groupTask);
						
						for (MarkerGroupDto markerDto : editAssessment.getGroupMarkerDtos()) {
							Assessor marker = markerDto.getMarker();
							if (markerDto.getToMark().contains(group)) {
								groupTask.addMarker(marker);
							}
						}
					}
					project.addAssessment(assessment);
					applyCoreAssessmentEdits(assessment, editAssessment);
				}
				
			} else {
				//Adding a new assessment
				GroupAssessment assessment = new GroupAssessment();
				for (Group group : project.getGroups()) {
					GroupTask groupTask = new GroupTask(group);
					assessment.addTask(groupTask);
					
					for (MarkerGroupDto markerDto : editAssessment.getGroupMarkerDtos()) {
						Assessor marker = markerDto.getMarker();
						if (markerDto.getToMark().contains(group)) {
							groupTask.addMarker(marker);
						}
					}
				}
				project.addAssessment(assessment);
				applyCoreAssessmentEdits(assessment, editAssessment);
			}
		}
		
		List<GroupAssessment> removed = new ArrayList<>(oldAssessments);
		removed.removeAll(presentAssessments);
		project.getAssessments().removeAll(removed);
	}
	
	private Optional<GroupAssessment> findByGroupAssessmentId(List<GroupAssessment> groupAssessments, Long id) {
		return groupAssessments.stream().filter(a -> a.getId() == id).findFirst();
	}
}
