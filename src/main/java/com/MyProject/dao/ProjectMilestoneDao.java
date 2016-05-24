package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.project.ProjectMilestoneEntity;
import com.MyProject.commons.dao.GenericDao;

public interface ProjectMilestoneDao extends GenericDao<ProjectMilestoneEntity, Long> {
	
	List<ProjectMilestoneEntity> findByProject(ProjectEntity projectEntity);
	
	public boolean checkAvailable(String milestoneName);
	
	boolean checkAvailableByProject(ProjectEntity projectEntity, String nameMilestone);
	
	public Long countEntity();
	
	Long countEntityByProject(ProjectEntity projectEntity);
	
}
