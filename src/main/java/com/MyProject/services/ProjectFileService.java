package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.project.ProjectFileEntity;

public interface ProjectFileService {

	public void createProjectFile(ProjectEntity projectEntity);
	
	List<ProjectFileEntity> loadAll();
	
	void delete(ProjectFileEntity projectFileEntity, ProjectEntity projectEntity);
	
	void update(ProjectFileEntity projectFileEntity);
	
	ProjectFileEntity findById(Long id); 
	
}
