package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.commons.ContractorOrganizationEntity;
import com.MyProject.domain.project.ProjectEntity;

public interface ContractorOrganizationService {
	
	void createContractorOrganizationOnStartNewDataBase(ProjectEntity projectEntity);

	void createProjectContractorOrganization(ProjectEntity projectEntity);
	
	List<ContractorOrganizationEntity> loadAll();
	
	List<ContractorOrganizationEntity> findByProject(ProjectEntity projectEntity);
	
	void delete(ContractorOrganizationEntity ContractorOrganizationEntity, ProjectEntity projectEntity);
	
	void update(ContractorOrganizationEntity ContractorOrganizationEntity, ProjectEntity projectEntity);
	
	ContractorOrganizationEntity findById(Long id); 
	
}
