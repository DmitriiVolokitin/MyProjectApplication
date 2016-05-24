package com.MyProject.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.domain.user.UserSettingEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class UserSettingJpaDao extends GenericJpaDao<UserSettingEntity, Long> implements UserSettingDao {

	public UserSettingJpaDao() {
		super(UserSettingEntity.class);
	}

	public UserSettingEntity findByUser(UserEntity userEntity){

		Assert.notNull(userEntity);

		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.user = :userEntity").setParameter("userEntity", userEntity);

		try {
			return (UserSettingEntity) query.getSingleResult();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	public UserSettingEntity findByUserAndProject(UserEntity userEntity, ProjectEntity projectEntity){

		Assert.notNull(userEntity);

		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where (f.user = :userEntity) and (f.projectByDefault = :projectEntity)");

		query.setParameter("userEntity", userEntity);
		query.setParameter("projectEntity", projectEntity);

		try {
			return (UserSettingEntity) query.getSingleResult();
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

	public Long countEntityByUserAndProject(UserEntity userEntity, ProjectEntity projectEntity){

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u where (u.user = :userEntity) and (u.projectByDefault = :projectEntity)");
		query.setParameter("userEntity", userEntity);
		query.setParameter("projectEntity", projectEntity);

		return (Long) query.getSingleResult();

	}

}
