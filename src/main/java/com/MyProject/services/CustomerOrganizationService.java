package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.commons.CustomerOrganizationEntity;
import com.MyProject.domain.project.ProjectEntity;

public interface CustomerOrganizationService {
	
	void createCustomerOrganizationOnStartNewDataBase(ProjectEntity projectEntity);

	void createProjectCustomerOrganization(ProjectEntity projectEntity);
	
	List<CustomerOrganizationEntity> loadAll();
	
	List<CustomerOrganizationEntity> findByProject(ProjectEntity projectEntity);
	
	void delete(CustomerOrganizationEntity CustomerOrganizationEntity, ProjectEntity projectEntity);
	
	void update(CustomerOrganizationEntity CustomerOrganizationEntity, ProjectEntity projectEntity);
	
	CustomerOrganizationEntity findById(Long id); 
	
}
