package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.project.ProjectFileEntity;
import com.MyProject.commons.dao.GenericDao;

public interface ProjectFileDao extends GenericDao<ProjectFileEntity, Long> {
	
	List<ProjectFileEntity> findByProject(ProjectEntity projectEntity, boolean commons);
	
}
