package com.MyProject.services;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.domain.user.UserSettingEntity;

public interface UserSettingService {

	void createUserSetting(UserEntity userEntity, ProjectEntity projectEntity);
	
	UserSettingEntity findByUser(UserEntity userEntity);
	
	UserSettingEntity findByUserAndProject(UserEntity userEntity, ProjectEntity projectEntity);
	
	void delete(UserSettingEntity UserSettingEntity);
	
	void update(UserSettingEntity UserSettingEntity);
	
}
