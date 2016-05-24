package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.user.UserRoleEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class UserRoleJpaDao extends GenericJpaDao<UserRoleEntity, Long> implements UserRoleDao {

	public UserRoleJpaDao() {
		super(UserRoleEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<UserRoleEntity> findByTaskID(TaskEntity taskID) {
		
		Assert.notNull(taskID);

		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.task = :taskID").setParameter("taskID", taskID);

		try {
			return (List<UserRoleEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}
	
	public UserRoleEntity findByName(String nameUserRole) {
		
		Assert.notNull(nameUserRole);

		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.name = :nameUserRole").setParameter("nameUserRole", nameUserRole);

		try {
			return (UserRoleEntity) query.getSingleResult();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	public Long countEntity() {

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u");

		return (Long) query.getSingleResult();

	}

}
