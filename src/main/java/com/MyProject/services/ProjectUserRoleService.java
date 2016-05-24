package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.commons.ProjectUserRoleEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.domain.user.UserRoleEntity;

public interface ProjectUserRoleService {

	public void createProjectUserRole();
	
	List<ProjectUserRoleEntity> loadAll();
	
	List<ProjectEntity> findProject(UserEntity userEntity);
	
	List<UserEntity> findUser(ProjectEntity projectEntity);
	
	List<UserRoleEntity> findUserRole(ProjectEntity projectEntity, UserEntity userEntity);
	
	void delete(ProjectUserRoleEntity projectUserRoleEntity);
	
	void update(ProjectUserRoleEntity projectUserRoleEntity);
	
	ProjectUserRoleEntity findById(Long id); 
	
}
