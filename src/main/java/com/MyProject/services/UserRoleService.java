package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.user.UserRoleEntity;

public interface UserRoleService {

	void createUserRole(TaskEntity taskID);
	
	List<UserRoleEntity> loadAll();
	
	List<UserRoleEntity> findByTaskID(TaskEntity taskID);
	
	void delete(UserRoleEntity UserRoleEntity, TaskEntity taskID);
	
	void update(UserRoleEntity UserRoleEntity);
	
	UserRoleEntity findById(Long id); 
	
}
