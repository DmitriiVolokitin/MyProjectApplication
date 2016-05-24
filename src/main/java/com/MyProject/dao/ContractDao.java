package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.commons.ContractEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.commons.dao.GenericDao;

public interface ContractDao extends GenericDao<ContractEntity, Long> {
	
	List<ContractEntity> findByProject(ProjectEntity projectEntity);
	
	Long countEntity();
	
	Long countEntityByProject(ProjectEntity projectEntity);
	
	boolean checkAvailableByProject(ProjectEntity projectEntity, String contractName);
	
}
