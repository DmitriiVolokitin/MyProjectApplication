package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.user.UserEntity;
import com.MyProject.commons.dao.GenericJpaDao;

/**
 * Data access object JPA impl to work with User entity database operations.
 * 
 * @author Di
 */
public class UserJpaDao extends GenericJpaDao<UserEntity, Long> implements UserDao {

	public UserJpaDao() {
		super(UserEntity.class);
	}

	/**
	 * Queries database for user name availability
	 * 
	 * @param userName
	 * @return true if available
	 */
	public boolean checkAvailable(String userName) {

		Assert.notNull(userName);

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u where u.userName = :userName").setParameter("userName", userName);

		Long count = (Long) query.getSingleResult();

		return count < 1;

	}

	public boolean checkAvailableEmail(String email) {

		Assert.notNull(email);

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u where u.email = :email").setParameter("email", email);

		Long count = (Long) query.getSingleResult();

		return count < 1;

	}
	
	@SuppressWarnings("unchecked")
	public List<UserEntity> searchUsersByUserNameOrEmail(String searchText) {
		
		Assert.notNull(searchText);
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where (f.userName = :searchText) or (f.email = :searchText)").setParameter("searchText", searchText);
		
		try {
			return (List<UserEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return null;
	}

	/**
	 * Queries user by username
	 * 
	 * @param userName
	 * @return User entity
	 */
	public UserEntity loadUserByUserName(String userName) {

		Assert.notNull(userName);

		UserEntity user = null;

		Query query = getEntityManager().createQuery("select u from " + getPersistentClass().getSimpleName()
				+ " u where u.userName = :userName").setParameter("userName", userName);

		try {
			user = (UserEntity) query.getSingleResult();
		} catch(NoResultException e) {
			//do nothing
		}

		return user;
	}

}
