package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.project.ProjectCostSettingEntity;
import com.MyProject.domain.project.ProjectEntity;

public interface ProjectCostSettingService {

	void createProjectCostSettingOnStartNewDataBase(ProjectEntity projectEntity);
	
	void createProjectCostSetting(ProjectEntity projectEntity);
	
	List<ProjectCostSettingEntity> loadAll();
	
	List<ProjectCostSettingEntity> findByProject(ProjectEntity projectEntity) ;
	
	void delete(ProjectCostSettingEntity contract, ProjectEntity projectEntity);
	
	void update(ProjectCostSettingEntity ProjectCostSettingEntity);
	
	ProjectCostSettingEntity findById(Long id); 
	
}
