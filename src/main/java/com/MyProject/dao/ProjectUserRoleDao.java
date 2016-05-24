package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.commons.ProjectUserRoleEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.domain.user.UserRoleEntity;
import com.MyProject.commons.dao.GenericDao;

public interface ProjectUserRoleDao extends GenericDao<ProjectUserRoleEntity, Long> {
	
	Long countEntity();
	
	Long countEntityByUserAndUserRole(UserEntity userEntity, UserRoleEntity userRoleEntity);
	
	Long countEntityUserByProjectAndUserRole(ProjectEntity projectEntity, UserRoleEntity userRoleEntity);
	
	boolean checkAvailableRole(ProjectEntity projectEntity, UserEntity userEntity, UserRoleEntity userRoleEntity);
	
	List<ProjectEntity> findProject(UserEntity userEntity, boolean groupBY);
	
	List<ProjectEntity> findProjectByUserRoleProjectMnager(UserEntity userEntity, UserRoleEntity userRoleEntity, boolean groupBY);
	
	List<UserEntity> findUser(ProjectEntity projectEntity, boolean groupBY);
	
	List<UserRoleEntity> findUserRole(ProjectEntity projectEntity, UserEntity userEntity, boolean groupBY);
	
	List<ProjectUserRoleEntity> findEntityByProject(ProjectEntity projectEntity, boolean groupBY);
	
	List<ProjectUserRoleEntity> findEntityByProjectAndUser(ProjectEntity projectEntity, UserEntity userEntity, boolean groupBY);
	
	List<ProjectUserRoleEntity> findEntityByProjectUserAndUserRole(ProjectEntity projectEntity, UserEntity userEntity, UserRoleEntity userRoleEntity, boolean groupBY);
}
