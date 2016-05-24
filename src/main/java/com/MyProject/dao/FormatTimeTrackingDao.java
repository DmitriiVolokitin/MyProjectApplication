package com.MyProject.dao;

import com.MyProject.domain.commons.FormatTimeTrackingEntity;
import com.MyProject.domain.project.ProjectEntity;

import java.util.List;

import com.MyProject.commons.dao.GenericDao;

public interface FormatTimeTrackingDao extends GenericDao<FormatTimeTrackingEntity, Long> {
	
	FormatTimeTrackingEntity findByName(String nameFormatTimeTracking);
	
	List<FormatTimeTrackingEntity> findByProject(ProjectEntity projectEntity);
	
	FormatTimeTrackingEntity findByProjectAndName(ProjectEntity projectEntity, String nameFormatTimeTracking);
	
	public boolean checkAvailable(String formatTimeTrackingName);
	
	boolean checkAvailableByProject(ProjectEntity projectEntity, String nameFormatTimeTracking);
	
	public Long countEntity();
	
	Long countEntityByProject(ProjectEntity projectEntity);
	
}
