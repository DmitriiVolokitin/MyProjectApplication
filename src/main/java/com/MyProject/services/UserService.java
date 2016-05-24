package com.MyProject.services;

import javax.faces.event.AjaxBehaviorEvent;

import com.MyProject.domain.user.UserEntity;

/**
 * Service providing service methods to work with user data and entity.
 * 
 * @author Arthur Vin
 */
public interface UserService {

	/**
	 * Create user - persist to database
	 * 
	 * @param userEntity
	 * @return true if success
	 */
	boolean createUser(UserEntity userEntity);
	
	/**
	 * Check user name availability. UI ajax use.
	 * 
	 * @param ajax event
	 * @return
	 */
	
	boolean checkAvailable(AjaxBehaviorEvent event);
	
	boolean checkAvailableEmail(AjaxBehaviorEvent event);
	
	/**
	 * Retrieves full User record from database by user name
	 * 
	 * @param userName
	 * @return UserEntity
	 */
	UserEntity loadUserEntityByUsername(String userName);
}
