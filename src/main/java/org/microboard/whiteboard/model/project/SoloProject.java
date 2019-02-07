package org.microboard.whiteboard.model.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.microboard.whiteboard.dto.assessment.NewSoloAssessment;
import org.microboard.whiteboard.dto.project.NewSoloProject;
import org.microboard.whiteboard.dto.user.MarkerDto;
import org.microboard.whiteboard.dto.user.MarkerUserDto;
import org.microboard.whiteboard.model.assessment.SoloAssessment;
import org.microboard.whiteboard.model.task.SoloTask;
import org.microboard.whiteboard.model.user.Assessor;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.model.user.User;

@Entity
@DiscriminatorValue("solo")
public class SoloProject extends Project {
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy= "project")
	private List<SoloAssessment> assessments = new ArrayList<>();

	public SoloProject() {}
	
	public SoloProject(NewSoloProject project, UnitDirector creator) {
		super(project, creator);
		
		for (NewSoloAssessment newAssessment : project.getAssessments()) {
			String assessmentName = newAssessment.getName();
			String assessmentDesc = newAssessment.getDescription();
			
			SoloAssessment soloAssessment = new SoloAssessment();
			soloAssessment.setName(assessmentName);
			soloAssessment.setDescription(assessmentDesc);
			
			for (User user : project.getUnit().getCohort()) {
				SoloTask soloTask = new SoloTask();
				user.addTask(soloTask);
				for (MarkerUserDto markerDto : newAssessment.getMarkerDtos()) {
					Assessor marker = markerDto.getMarker();
					if (markerDto.getToMark().contains(user)) {
						soloTask.addMarker(marker);
					}
				}
				soloAssessment.addTask(soloTask);
			}
			addAssessment(soloAssessment);
		}
		
		creator.addProject(this);
		
	}

	public List<SoloAssessment> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<SoloAssessment> assessments) {
		this.assessments = assessments;
	}
	
	public void addAssessment(SoloAssessment assessment) {
		assessment.setProject(this);
		this.assessments.add(assessment);
	}
	
}
