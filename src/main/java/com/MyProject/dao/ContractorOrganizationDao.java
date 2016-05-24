package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.commons.ContractorOrganizationEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.commons.dao.GenericDao;

public interface ContractorOrganizationDao extends GenericDao<ContractorOrganizationEntity, Long> {
	
	List<ContractorOrganizationEntity> findByProject(ProjectEntity projectEntity);
	
	Long countEntity();
	
	Long countEntityByProject(ProjectEntity projectEntity);
	
	boolean checkAvailableByProject(ProjectEntity projectEntity, String contractorOrganizationName);
	
}
