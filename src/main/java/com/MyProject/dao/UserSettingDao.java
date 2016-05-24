package com.MyProject.dao;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.domain.user.UserSettingEntity;
import com.MyProject.commons.dao.GenericDao;

public interface UserSettingDao extends GenericDao<UserSettingEntity, Long> {
	
	UserSettingEntity findByUser(UserEntity userEntity);
	
	UserSettingEntity findByUserAndProject(UserEntity userEntity, ProjectEntity projectEntity);
	
	Long countEntity();
	
	Long countEntityByUserAndProject(UserEntity userEntity, ProjectEntity projectEntity);
	
}
