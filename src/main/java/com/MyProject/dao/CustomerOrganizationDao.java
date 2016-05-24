package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.commons.CustomerOrganizationEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.commons.dao.GenericDao;

public interface CustomerOrganizationDao extends GenericDao<CustomerOrganizationEntity, Long> {
	
	List<CustomerOrganizationEntity> findByProject(ProjectEntity projectEntity);
	
	Long countEntity();
	
	Long countEntityByProject(ProjectEntity projectEntity);
	
	boolean checkAvailableByProject(ProjectEntity projectEntity, String customerOrganizationName);
	
}
