package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.MyProject.domain.commons.ProjectUserRoleEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.domain.user.UserRoleEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class ProjectUserRoleJpaDao extends GenericJpaDao<ProjectUserRoleEntity, Long> implements ProjectUserRoleDao {

	public ProjectUserRoleJpaDao() {
		super(ProjectUserRoleEntity.class);
	}

	public Long countEntity() {

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u");

		return (Long) query.getSingleResult();

	}
	
	public Long countEntityByUserAndUserRole(UserEntity userEntity, UserRoleEntity userRoleEntity){
		
		Query query = getEntityManager().createQuery("select count(*) from " + getPersistentClass().getSimpleName()
					+ " u where (u.user = :userEntity) and (u.userRole = :userRoleEntity)" + " GROUP BY u.user, u.userRole");

		query.setParameter("userEntity", userEntity);
		query.setParameter("userRoleEntity", userRoleEntity);

		return (Long) query.getSingleResult();
		
	}
	
	public Long countEntityUserByProjectAndUserRole(ProjectEntity projectEntity, UserRoleEntity userRoleEntity){
		
		Query query = getEntityManager().createQuery("select count(*) from " + getPersistentClass().getSimpleName()
					+ " u where (u.project = :projectEntity) and (u.userRole = :userRoleEntity)" + " GROUP BY u.project, u.userRole");

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("userRoleEntity", userRoleEntity);

		return (Long) query.getSingleResult();
		
	}

	public boolean checkAvailableRole(ProjectEntity projectEntity, UserEntity userEntity, UserRoleEntity userRoleEntity) {

		Query query;

		query = getEntityManager().createQuery("select count(*) from " + getPersistentClass().getSimpleName()
				+ " f where (f.user = :userEntity) and (f.project = :projectEntity) and (f.userRole = :userRoleEntity)");

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("userEntity", userEntity);
		query.setParameter("userRoleEntity", userRoleEntity);

		try {
			Long countEntity = (Long) query.getSingleResult();
			if (countEntity > 0) {
				return true; 
			}
			else {
				return false; 
			}

		} catch(NoResultException e) {
			return false;
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectEntity> findProject(UserEntity userEntity, boolean groupBY) {

		Query query;

		if(groupBY) {
			query = getEntityManager().createQuery("Select f.project FROM " + getPersistentClass().getSimpleName()
					+ " f where f.user = :userEntity" + " GROUP BY f.project").setParameter("userEntity", userEntity);
		}
		else{
			query = getEntityManager().createQuery("Select project FROM " + getPersistentClass().getSimpleName()
					+ " f where f.user = :userEntity").setParameter("userEntity", userEntity);
		}

		try {
			return (List<ProjectEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;

	}

	@SuppressWarnings("unchecked")
	public List<UserEntity> findUser(ProjectEntity projectEntity, boolean groupBY) {

		Query query;

		if(groupBY) {
			query = getEntityManager().createQuery("Select f.user FROM " + getPersistentClass().getSimpleName()
					+ " f where f.project = :projectEntity" + " GROUP BY f.user").setParameter("projectEntity", projectEntity);
		}
		else{
			query = getEntityManager().createQuery("Select user FROM " + getPersistentClass().getSimpleName()
					+ " f where f.project = :projectEntity").setParameter("projectEntity", projectEntity);
		}

		try {
			return (List<UserEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;

	}

	@SuppressWarnings("unchecked")
	public List<ProjectUserRoleEntity> findEntityByProjectAndUser(ProjectEntity projectEntity, UserEntity userEntity, boolean groupBY) {

		Query query;

		if(groupBY) {
			query = getEntityManager().createQuery("Select f.id FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.user = :userEntity) and (f.project = :projectEntity)" + " GROUP BY f.id");
		}
		else{
			query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.user = :userEntity) and (f.project = :projectEntity)");
		}

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("userEntity", userEntity);


		try {
			return (List<ProjectUserRoleEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;

	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectEntity> findProjectByUserRoleProjectMnager(UserEntity userEntity, UserRoleEntity userRoleEntity, boolean groupBY) {

		Query query;

		if(groupBY) {
			query = getEntityManager().createQuery("Select f.project FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.user = :userEntity) and (f.userRole = :userRoleEntity)" + " GROUP BY f.project");
		}
		else{
			query = getEntityManager().createQuery("Select f.project FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.user = :userEntity) and (f.userRole = :userRoleEntity)");
		}

		query.setParameter("userEntity", userEntity);
		query.setParameter("userRoleEntity", userRoleEntity);


		try {
			return (List<ProjectEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;

	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectUserRoleEntity> findEntityByProject(ProjectEntity projectEntity, boolean groupBY) {
		
		Query query;

		if(groupBY) {
			query = getEntityManager().createQuery("Select f.id FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.project = :projectEntity)" + " GROUP BY f.id");
		}
		else{
			query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.project = :projectEntity)");
		}

		query.setParameter("projectEntity", projectEntity);

		try {
			return (List<ProjectUserRoleEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ProjectUserRoleEntity> findEntityByProjectUserAndUserRole(ProjectEntity projectEntity, UserEntity userEntity, UserRoleEntity userRoleEntity, boolean groupBY) {

		Query query;

		if(groupBY) {
			query = getEntityManager().createQuery("Select f.id FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.user = :userEntity) and (f.project = :projectEntity) and (f.userRole = :userRoleEntity)" + " GROUP BY f.id");
		}
		else{
			query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.user = :userEntity) and (f.project = :projectEntity) and (f.userRole = :userRoleEntity)");
		}

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("userEntity", userEntity);
		query.setParameter("userRoleEntity", userRoleEntity);

		try {
			return (List<ProjectUserRoleEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;

	}

	@SuppressWarnings("unchecked")
	public List<UserRoleEntity> findUserRole(ProjectEntity projectEntity, UserEntity userEntity, boolean groupBY) {

		Query query;

		if(groupBY) {
			query = getEntityManager().createQuery("Select f.userRole FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.project = :projectEntity) and (f.user = :userEntity)" + " GROUP BY f.userRole");
		}
		else{
			query = getEntityManager().createQuery("Select userRole FROM " + getPersistentClass().getSimpleName()
					+ " f where (f.project = :projectEntity) and (f.user = :userEntity)");
		}

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("userEntity", userEntity);

		try {
			return (List<UserRoleEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;

	}

}
