package com.MyProject.services;

import com.MyProject.domain.user.UserEntity;

/**
 * Provides processing service to set user authentication session
 * 
 * @author Di
 */
public interface UserAuthenticationProviderService {

	/**
	 * Process user authentication
	 * 
	 * @param user
	 * @return
	 */
	boolean processUserAuthentication(UserEntity user);
}
