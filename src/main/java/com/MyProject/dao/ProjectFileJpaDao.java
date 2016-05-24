package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.project.ProjectFileEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class ProjectFileJpaDao extends GenericJpaDao<ProjectFileEntity, Long> implements ProjectFileDao {

//	projectFileDao.findByProject
	
	public ProjectFileJpaDao() {
		super(ProjectFileEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectFileEntity> findByProject(ProjectEntity projectEntity, boolean commons) {

		Query query;
		
		if(commons){
			query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.project = :projectEntity) and (f.commons = :commons)");
			query.setParameter("projectEntity", projectEntity);
			query.setParameter("commons", commons);
		}
		else{
			
			query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.project = :projectEntity)");
			query.setParameter("projectEntity", projectEntity);
		}

		try {
			return (List<ProjectFileEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

}
