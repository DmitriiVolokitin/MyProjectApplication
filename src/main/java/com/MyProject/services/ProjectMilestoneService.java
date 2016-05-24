package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.project.ProjectMilestoneEntity;

public interface ProjectMilestoneService {
	
	void createProjectMilestoneOnStartNewDataBase(ProjectEntity projectEntity);

	void createProjectMilestone(ProjectEntity projectEntity, ProjectMilestoneEntity projectMilestoneEntity);
	
	List<ProjectMilestoneEntity> loadAll();
	
	List<ProjectMilestoneEntity> findByProject(ProjectEntity projectEntity);
	
	void delete(ProjectMilestoneEntity ProjectMilestoneEntity, ProjectEntity projectEntity);
	
	void update(ProjectMilestoneEntity ProjectMilestoneEntity);
	
	ProjectMilestoneEntity findById(Long id); 
	
}
