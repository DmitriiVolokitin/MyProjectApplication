package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.project.ProjectCostSettingEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.commons.dao.GenericDao;

public interface ProjectCostSettingDao extends GenericDao<ProjectCostSettingEntity, Long> {
	
	List<ProjectCostSettingEntity> findByProject(ProjectEntity projectEntity);
	
	Long countEntity();
	
	Long countEntityByProject(ProjectEntity projectEntity);
	
}
