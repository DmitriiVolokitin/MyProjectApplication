package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.commons.ContractEntity;
import com.MyProject.domain.project.ProjectEntity;

public interface ContractService {

	void createContractOnStartNewDataBase(ProjectEntity projectEntity);
	
	void createProjectContract(ProjectEntity projectEntity);
	
	List<ContractEntity> loadAll();
	
	List<ContractEntity> findByProject(ProjectEntity projectEntity) ;
	
	void delete(ContractEntity contract, ProjectEntity projectEntity);
	
	void update(ContractEntity ContractEntity);
	
	ContractEntity findById(Long id); 
	
}
