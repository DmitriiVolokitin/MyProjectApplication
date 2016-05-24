package com.MyProject.services;

import java.util.List;

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskTimeTrackingEntity;
import com.MyProject.domain.user.UserEntity;

public interface TaskTimeTrackingService {
	
	void setAccepted(TaskTimeTrackingEntity taskTimeTrackingEntity, UserEntity userEntity);

	void createTaskTimeTracking(TaskEntity taskID); 
	
	List<TaskTimeTrackingEntity> loadAll();
	
	void delete(TaskTimeTrackingEntity taskTimeTrackingEntity, TaskEntity taskID);
	
	void update(TaskTimeTrackingEntity taskTimeTrackingEntity);
	
	TaskTimeTrackingEntity findById(Long id); 
	
}
