package org.microboard.whiteboard.services.project;

import java.io.File;
import java.util.ArrayList;
import org.microboard.whiteboard.dto.assessment.GroupAssessmentDto;
import org.microboard.whiteboard.dto.project.GroupProjectDto;
import org.microboard.whiteboard.dto.user.MarkerGroupDto;
import org.microboard.whiteboard.model.project.GroupProject;
import org.microboard.whiteboard.model.project.visitors.ProjectFolderCreator;
import org.microboard.whiteboard.model.user.UnitDirector;
import org.microboard.whiteboard.pojos.ProjectEditApplyer;
import org.microboard.whiteboard.pojos.ProjectTemplateMaker;
import org.microboard.whiteboard.services.user.UnitDirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupProjectService extends BaseProjectService<GroupProject> {
} 
