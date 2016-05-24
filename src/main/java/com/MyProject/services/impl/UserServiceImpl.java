package com.MyProject.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.primefaces.context.RequestContext;

import com.MyProject.domain.user.UserEntity;
import com.MyProject.email.Email;
import com.MyProject.services.UserService;
import com.MyProject.dao.UserDao;

/**
 * Service providing service methods to work with user data and entity.
 * 
 * @author Di
 */

@javax.faces.bean.ManagedBean
@javax.faces.bean.ApplicationScoped
public class UserServiceImpl implements UserService, UserDetailsService, Serializable {

	private static final long serialVersionUID = -6392545542389744944L;

	private UserDao userDao;
	private UserEntity selectedUser = new UserEntity();
	private UserEntity editUser = new UserEntity();
	private List<UserEntity> users = new ArrayList<UserEntity>();
	private UserEntity userADD = new UserEntity();
	private UserEntity currentUser = new UserEntity();

	@PostConstruct
	public void init() {
		System.out.println("New bean UserServiceImpl");
		setUsers(userDao.findAll());
	}

	public void clear() {
		setUserADD(new UserEntity());
	}

	public void reset() {
		RequestContext.getCurrentInstance().reset("userEditForm:panel");
	}

	public void setPreviousSlectetUserValue(UserEntity user) {
		setSelectedUser(new UserEntity());
		System.out.println(user.getFirstName());
		System.out.println(userDao.findById(user.getId()).getFirstName());
		setSelectedUser(userDao.findById(user.getId()));
		setSelectedUser(new UserEntity());
	}

	/**
	 * Create user - persist to database
	 * 
	 * @param userEntity
	 * @return true if success
	 */
	public boolean createUser(UserEntity userEntity) {

		if (!userDao.checkAvailable(userEntity.getUserName())) {
			FacesMessage message = constructErrorMessage(String.format(getMessageBundle().getString("userExistsMsg"), userEntity.getUserName()), null);
			getFacesContext().addMessage(null, message);

			return false;
		}

		try {
			userDao.save(userEntity);
			Email emailSend = new Email("SendEmailNewUser", userEntity);
			emailSend.start();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);

			return false;
		}

		return true;
	}

	/**
	 * Check user name availability. UI ajax use.
	 * 
	 * @param ajax event
	 * @return
	 */
	public boolean checkAvailable(AjaxBehaviorEvent event) {

		InputText inputText = (InputText) event.getSource();
		String value = (String) inputText.getValue();

		boolean available = userDao.checkAvailable(value);

		if (!available) {
			FacesMessage message = constructErrorMessage(null, String.format(getMessageBundle().getString("userExistsMsg"), value));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		} else {
			FacesMessage message = constructInfoMessage(null, String.format(getMessageBundle().getString("userAvailableMsg"), value));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		}

		return available;
	}

	public void validateUserNameFromBean(FacesContext arg0, UIComponent arg1, Object val)
			throws ValidatorException {
		String userName = (String) val;

		if (!userDao.checkAvailable(userName)) {
			FacesMessage message = new FacesMessage();
			message.setDetail("Please enter a valid User Name [from validator bean]");
			message.setSummary("User Name not valid [from Validator bean]");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

	public boolean checkAvailableEmail(AjaxBehaviorEvent event) {

		InputText inputText = (InputText) event.getSource();
		String value = (String) inputText.getValue();

		boolean available = userDao.checkAvailableEmail(value);

		if (!available) {
			FacesMessage message = constructErrorMessage(null, String.format(getMessageBundle().getString("emailExistsMsg"), value));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		} else {
			FacesMessage message = constructInfoMessage(null, String.format(getMessageBundle().getString("emailAvailableMsg"), value));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		}

		return available;
	}

	public void validateEmailFromBean(FacesContext arg0, UIComponent arg1, Object val)
			throws ValidatorException {

		String userName = "";
		
		if (arg1.getAttributes().get("userName") != null){
			userName = (String) arg1.getAttributes().get("userName");
		}
		
		String email = (String) val;
		UserEntity currentUser = userDao.loadUserByUserName(userName);

		if(currentUser != null){
			if(!currentUser.getEmail().equals(email)) {

				if (!userDao.checkAvailableEmail(email)) {
					FacesMessage message = new FacesMessage();
					message.setDetail("Please enter a valid email [from validator bean]");
					message.setSummary("Email not valid [from Validator bean]");
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(message);
				}
			}
		}
		
		if (userName.equals("")){
			
			if(!userDao.checkAvailableEmail(email)) {
				FacesMessage message = new FacesMessage();
				message.setDetail("Please enter a valid email [from validator bean]");
				message.setSummary("Email not valid [from Validator bean]");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
			
		}
	}

	/**
	 * Construct UserDetails instance required by spring security
	 */
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		UserEntity user = userDao.loadUserByUserName(userName);

		if (user == null) {
			throw new UsernameNotFoundException(String.format(getMessageBundle().getString("badCredentials"), userName));
		}

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		User userDetails = new User(user.getUserName(), user.getPassword(), authorities);

		return userDetails;
	}

	public void delete(UserEntity UserEntity) {

		try {
			userDao.delete(UserEntity);
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	/**
	 * Retrieves full User record from database by user name
	 * 
	 * @param userName
	 * @return UserEntity
	 */
	public void update(UserEntity userEntity) {

		try {
			userDao.update(userEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	protected FacesMessage constructErrorMessage(String message, String detail){
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
	}

	protected FacesMessage constructInfoMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_INFO, message, detail);
	}

	protected FacesMessage constructFatalMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_FATAL, message, detail);
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Task User Selected", ((UserEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Task User Unselect", ((UserEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected ResourceBundle getMessageBundle() {
		return ResourceBundle.getBundle("message-labels");
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserEntity getUserADD() {
		return userADD;
	}

	public void setUserADD(UserEntity userADD) {
		this.userADD = userADD;
	}

	public UserEntity getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserEntity selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<UserEntity> getUsers() {
		return this.users;
	}

	public UserEntity getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserEntity currentUser) {
		this.currentUser = currentUser;
	}

	public UserEntity loadUserEntityByUsername(String userName) {
		return userDao.loadUserByUserName(userName);
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public UserEntity getEditUser() {
		return editUser;
	}

	public void setEditUser(UserEntity editUser) {
		this.editUser = editUser;
	}

}
