package com.MyProject.dao;

import java.util.List;

import com.MyProject.domain.user.UserEntity;
import com.MyProject.commons.dao.GenericDao;

/**
 * Data access object interface to work with User entity database operations.
 * 
 * @author Di
 */
public interface UserDao extends GenericDao<UserEntity, Long> {

	/**
	 * Queries database for user name availability
	 * 
	 * @param userName
	 * @return true if available
	 */
	boolean checkAvailable(String userName);
	
	boolean checkAvailableEmail(String email);
	
	List<UserEntity> searchUsersByUserNameOrEmail(String searchText);
	
	/**
	 * Queries user by username
	 * 
	 * @param userName
	 * @return User entity
	 */
	UserEntity loadUserByUserName(String userName);
}
