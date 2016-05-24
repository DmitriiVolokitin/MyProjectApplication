package com.MyProject.services;

import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import com.MyProject.domain.commons.FormatTimeTrackingEntity;
import com.MyProject.domain.project.ProjectEntity;

public interface FormatTimeTrackingService {
	
	public void createFormatTimeTracking(ProjectEntity projectEntity, FormatTimeTrackingEntity formatTimeTrackingEntity);
	
	List<FormatTimeTrackingEntity> loadAll();
	
	void delete(ProjectEntity projectEntity, FormatTimeTrackingEntity formatTimeTrackingEntity);
	
	void update(ProjectEntity projectEntity, FormatTimeTrackingEntity formatTimeTrackingEntity);
	
	FormatTimeTrackingEntity findById(Long id);
	
	boolean checkAvailableByProject(AjaxBehaviorEvent event, ProjectEntity projectEntity);
	
}
