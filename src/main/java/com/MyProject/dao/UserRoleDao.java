package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.user.UserRoleEntity;
import com.MyProject.commons.dao.GenericDao;

public interface UserRoleDao extends GenericDao<UserRoleEntity, Long> {
	
	List<UserRoleEntity> findByTaskID(TaskEntity taskID);
	
	public Long countEntity();
	
	UserRoleEntity findByName(String nameUserRole);
	
}
