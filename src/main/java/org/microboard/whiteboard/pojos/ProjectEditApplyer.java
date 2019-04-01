package org.microboard.whiteboard.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.http.codec.multipart.SynchronossPartHttpMessageReader;

public class ProjectEditApplyer {
	private List<String> editSummary = new ArrayList<>();

	private void applyCoreProjectEdits(Project project, ProjectDto<?> edits) {
		Long id = project.getId();
		String name = edits.getName();
		String description = edits.getDescription();
		Unit unit = edits.getUnit();

		project.setId(id);
		project.setName(name);
		project.setDescription(description);
		// project.setHelpers(helpers);
		project.setUnit(unit);

		List<UnitDirector> removed = new ArrayList<>(project.getHelpers());
		removed.removeAll(edits.getHelpers());

		project.getHelpers().removeAll(removed);

		List<UnitDirector> added = new ArrayList<>(edits.getHelpers());
		added.removeAll(project.getHelpers());

		for (UnitDirector helper : added) {
			project.addHelper(helper);
		}
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

		if (newUnit) {
			editSummary.add(
					"changed unit from \"" + project.getUnit().getUnitName() + "\"" + edits.getUnit().getUnitName());
		}

		applyCoreProjectEdits(project, edits);
		for (SoloAssessmentDto editAssessment : edits.getAssessments()) {
			Long assessmentId = editAssessment.getId();
			if (assessmentId != null) {

				// Editing an existing assessment
				if (!newUnit) {
					Optional<SoloAssessment> maybeAssessment = findSoloAssessmentById(oldAssessments, assessmentId);
					if (maybeAssessment.isPresent()) {
						SoloAssessment assessment = findSoloAssessmentById(oldAssessments, assessmentId).get();
						// If new unit, remove all tasks and add new tasks for members of the new
						// cohort.
						if (newUnit) {
							assessment.getTasks().removeAll(assessment.getTasks());
							for (User user : project.getUnit().getCohort()) {
								SoloTask newTask = new SoloTask();
								user.addTask(newTask);
								assessment.addTask(newTask);
	
							}
						}
	
						// Add markers to each task
						for (SoloTask soloTask : assessment.getTasks()) {
							List<Assessor> oldMarkers = soloTask.getMarkers();
							Set<Assessor> presentMarkers = new HashSet<>();
	
							User accountable = soloTask.getAccountable();
							for (MarkerUserDto markerDto : editAssessment.getSoloMarkerDtos()) {
								Assessor marker = markerDto.getMarker();
	
								if (markerDto.getToMark().contains(accountable)) {
									if (!soloTask.getMarkers().contains(marker)) {
										soloTask.addMarker(marker);
										editSummary.add("asigned marker \"" + marker.getName() + "\" to \""
												+ soloTask.getAccountable().getName() + "\" on assessment " + "\""
												+ assessment.getName() + "\"");
									}
									presentMarkers.add(marker);
								}
							}
	
							List<Assessor> removedMarkers = new ArrayList<>(oldMarkers);
							removedMarkers.removeAll(presentMarkers);
							for (Assessor assessor : removedMarkers) {
								soloTask.removeMarker(assessor);
								editSummary.add("removed marker \"" + assessor.getName() + "\" from marking \""
										+ soloTask.getAccountable().getName() + "\" on assessment " + "\""
										+ assessment.getName() + "\"");
							}
						}
						presentAssessments.add(assessment);
						applyCoreAssessmentEdits(assessment, editAssessment);
					} else {
						// Adding a new assessment (save as new)
						editSummary.add("Added new assessment \"" + editAssessment.getName() + "\"");
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
			} else {
				// Adding a new assessment
				editSummary.add("Added new assessment \"" + editAssessment.getName() + "\"");
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
		for (SoloAssessment toRemove : removed) {
			editSummary.add("removed assessment \"" + toRemove.getName() + "\"");
			project.getAssessments().remove(toRemove);
		}
	}

	private Optional<SoloAssessment> findSoloAssessmentById(List<SoloAssessment> soloAssessments, Long id) {
		return soloAssessments.stream().filter(a -> a.getId().equals(id)).findFirst();
	}

	public void applyEdits(GroupProject project, GroupProjectDto edits) {
		boolean newUnit = false;
		if (project.getUnit() != null) {
			newUnit = project.getUnit().getId() != edits.getUnit().getId();
		}

		// If new unit, remove all groups.
		if (newUnit) {
			project.getGroups().removeAll(project.getGroups());
			editSummary.add(
					"changed unit from \"" + project.getUnit().getUnitName() + "\"" + edits.getUnit().getUnitName());
		}

		List<Group> oldGroups = new ArrayList<>(project.getGroups());
		List<Group> presentGroups = new ArrayList<>();

		for (Group newGroup : edits.getGroups()) {
			Long groupId = newGroup.getId();
			if (groupId != null) {
				Optional<Group> maybeGroup = findByGroupId(project.getGroups(), groupId);
				if (!newUnit) {
					if (maybeGroup.isPresent()) {
						Group oldGroup = maybeGroup.get();
						oldGroup.setName(newGroup.getName());
						presentGroups.add(oldGroup);
						List<User> toRemove = new ArrayList<>();
						for (User member : oldGroup.getMembers()) {
							if (!newGroup.getMembers().contains(member)) {
								toRemove.add(member);
							}
						}
	
						for (User user : toRemove) {
							oldGroup.getMembers().remove(user);
							user.getGroups().remove(oldGroup);
							for (GroupTask groupTask : oldGroup.getTasks()) {
								groupTask.removeIndividualFeedback(user);
							}
							editSummary.add("removed user \"" + user.getName() + "\" from  \"" + oldGroup.getName() + "\"");
	
						}
	
						for (User member : newGroup.getMembers()) {
							if (!oldGroup.getMembers().contains(member)) {
								editSummary.add("added user \"" + member.getName() + "\" to  \"" + oldGroup.getName() + "\"");
								oldGroup.addMember(member);
								for (GroupTask groupTask : oldGroup.getTasks()) {
									groupTask.addIndividualFeedback(member);
								}
							}
						}
					} else {
						Group group = new Group();
						group.setName(newGroup.getName());
						group.setMembers(newGroup.getMembers());
						for (User member : newGroup.getMembers()) {
							member.getGroups().add(group);
						}
						project.addGroup(group);
						editSummary.add("added group \"" + group.getName() + "\"");
					}
				} 
			} else {
				for (User member : newGroup.getMembers()) {
					member.getGroups().add(newGroup);
				}
				project.addGroup(newGroup);
				editSummary.add("added group \"" + newGroup.getName() + "\"");
			}
		}

		List<Group> removedGroups = new ArrayList<>(oldGroups);
		removedGroups.removeAll(presentGroups);

		for (Group toRemove : removedGroups) {
			project.getGroups().remove(toRemove);
			editSummary.add("removed group \"" + toRemove.getName() + "\"");
		}

		List<GroupAssessment> oldAssessments = new ArrayList<>(project.getAssessments());
		List<GroupAssessment> presentAssessments = new ArrayList<>();

		applyCoreProjectEdits(project, edits);
		for (GroupAssessmentDto editAssessment : edits.getAssessments()) {
			Long assessmentId = editAssessment.getId();
			if (assessmentId != null) {
				// Editing an existing assessment
				if (!newUnit) {
					Optional<GroupAssessment> maybeAssessment = findByGroupAssessmentId(oldAssessments, assessmentId);
					if (maybeAssessment.isPresent()) {
						GroupAssessment assessment = maybeAssessment.get();
						// Add markers to each task
						for (GroupTask groupTask : assessment.getTasks()) {
							List<Assessor> oldMarkers = groupTask.getMarkers();
							Set<Assessor> presentMarkers = new HashSet<>();
	
							Group accountable = groupTask.getAccountable();
							for (MarkerGroupDto markerDto : editAssessment.getGroupMarkerDtos()) {
								Assessor marker = markerDto.getMarker();
								for (Group group : markerDto.getToMark()) {
									if (group.getId() == accountable.getId()) {
										if (!groupTask.getMarkers().contains(marker)) {
											groupTask.addMarker(marker);
											editSummary.add("asigned marker \"" + marker.getName() + "\" to \""
													+ groupTask.getAccountable().getName() + "\"");
										}
										presentMarkers.add(marker);
										break;
									}
								}
							}
	
							List<Assessor> removedMarkers = new ArrayList<>(oldMarkers);
							removedMarkers.removeAll(presentMarkers);
							for (Assessor assessor : removedMarkers) {
								groupTask.removeMarker(assessor);
								editSummary.add("removed marker \"" + assessor.getName() + "\" from marking \""
										+ groupTask.getAccountable().getName() + "\"");
							}
	
						}
						presentAssessments.add(assessment);
						applyCoreAssessmentEdits(assessment, editAssessment);
					} else {
						// Adding a new assessment (save as new)
						editSummary.add("Added new assessment \"" + editAssessment.getName() + "\"");
						GroupAssessment assessment = new GroupAssessment();
						for (Group group : project.getGroups()) {
							GroupTask groupTask = new GroupTask();

							for (User member : group.getMembers()) {
								groupTask.addIndividualFeedback(member);
							}
							assessment.addTask(groupTask);
							group.addTask(groupTask);

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
			} else {
				// Adding a new assessment
				editSummary.add("Added new assessment \"" + editAssessment.getName() + "\"");
				GroupAssessment assessment = new GroupAssessment();
				for (Group group : project.getGroups()) {
					GroupTask groupTask = new GroupTask();

					for (User member : group.getMembers()) {
						groupTask.addIndividualFeedback(member);
					}
					assessment.addTask(groupTask);
					group.addTask(groupTask);

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
		for (GroupAssessment toRemove : removed) {
			editSummary.add("removed assessment \"" + toRemove.getName() + "\"");
			project.getAssessments().remove(toRemove);
		}
	}

	private Optional<GroupAssessment> findByGroupAssessmentId(List<GroupAssessment> groupAssessments, Long id) {
		return groupAssessments.stream().filter(a -> a.getId().equals(id)).findFirst();
	}

	private Optional<Group> findByGroupId(List<Group> group, Long id) {
		return group.stream().filter(a -> a.getId().equals(id)).findFirst();
	}

	public List<String> getEditSummary() {
		return editSummary;
	}
}
