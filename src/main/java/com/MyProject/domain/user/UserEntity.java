package com.MyProject.domain.user;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.MyProject.domain.commons.ProjectUserRoleEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskHistoryEntity;
import com.MyProject.domain.task.TaskStatusHistoryEntity;
import com.MyProject.domain.task.TaskTimeTrackingEntity;

@Entity
@Table(name="users")
public class UserEntity implements Serializable {
	
	private static final long serialVersionUID = 568379222048217476L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_ID", unique = true, nullable = false)
	private Long id;
	private String firstName;
	private String lastName;
	private String userName;
	private String displayName;
	private String password;
	@Column(name="user_Email", length = 255, nullable = false)
	private String email;
	@Column(name="user_mobilePhone", length = 255, nullable = true)
	private String mobilePhone;
	@Column(name="user_workPhone", length = 255, nullable = true)
	private String workPhone;
	@Column(name="user_another", length = 255, nullable = true)
	private String another;
	
	@OneToMany(mappedBy="user")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<ProjectUserRoleEntity>projectUserRoleUser;
	
	@OneToMany(mappedBy="userInitiator")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskEntity> taskUserInitiator;
	
	@OneToMany(mappedBy="userResponsible")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskEntity> taskUserResponsible;
	
	@OneToMany(mappedBy="usercurrentExecutor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskEntity> taskUsercurrentExecutor;
	
	@OneToMany(mappedBy="user")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<UserSettingEntity> userSettingUser;
	
	@OneToMany(mappedBy="changeAuthor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskHistoryEntity> taskHistoryChangeAuthor;
	
	@OneToMany(mappedBy="userAuthor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskStatusHistoryEntity> taskStatusHistoryUserAuthor;
	
	@OneToMany(mappedBy="userExecutor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskStatusHistoryEntity> taskStatusHistoryUserExecutor;
	
	@OneToMany(mappedBy="userAuthor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskTimeTrackingEntity> taskTimeTrackingUserAuthor;
	
	@OneToMany(mappedBy="userAccepted")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskTimeTrackingEntity> taskTimeTrackingUserAccepted;
	
	public String getFullUserName() {
		return getFirstName() + " " + getLastName();  
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		this.displayName = this.firstName + " " + this.lastName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
		this.displayName = this.firstName + " " + this.lastName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		PasswordEncoder crypto = new Md5PasswordEncoder();
		this.password = crypto.encodePassword(password, null);
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getAnother() {
		return another;
	}

	public void setAnother(String another) {
		this.another = another;
	}

	public Set<ProjectUserRoleEntity> getProjectUserRoleUser() {
		return projectUserRoleUser;
	}

	public void setProjectUserRoleUser(Set<ProjectUserRoleEntity> projectUserRoleUser) {
		this.projectUserRoleUser = projectUserRoleUser;
	}

	public Set<TaskEntity> getTaskUserInitiator() {
		return taskUserInitiator;
	}

	public void setTaskUserInitiator(Set<TaskEntity> taskUserInitiator) {
		this.taskUserInitiator = taskUserInitiator;
	}

	public Set<TaskEntity> getTaskUserResponsible() {
		return taskUserResponsible;
	}

	public void setTaskUserResponsible(Set<TaskEntity> taskUserResponsible) {
		this.taskUserResponsible = taskUserResponsible;
	}

	public Set<TaskEntity> getTaskUsercurrentExecutor() {
		return taskUsercurrentExecutor;
	}

	public void setTaskUsercurrentExecutor(Set<TaskEntity> taskUsercurrentExecutor) {
		this.taskUsercurrentExecutor = taskUsercurrentExecutor;
	}

	public Set<UserSettingEntity> getUserSettingUser() {
		return userSettingUser;
	}

	public void setUserSettingUser(Set<UserSettingEntity> userSettingUser) {
		this.userSettingUser = userSettingUser;
	}

	public Set<TaskHistoryEntity> getTaskHistoryChangeAuthor() {
		return taskHistoryChangeAuthor;
	}

	public void setTaskHistoryChangeAuthor(Set<TaskHistoryEntity> taskHistoryChangeAuthor) {
		this.taskHistoryChangeAuthor = taskHistoryChangeAuthor;
	}

	public Set<TaskStatusHistoryEntity> getTaskStatusHistoryUserAuthor() {
		return taskStatusHistoryUserAuthor;
	}

	public void setTaskStatusHistoryUserAuthor(Set<TaskStatusHistoryEntity> taskStatusHistoryUserAuthor) {
		this.taskStatusHistoryUserAuthor = taskStatusHistoryUserAuthor;
	}

	public Set<TaskStatusHistoryEntity> getTaskStatusHistoryUserExecutor() {
		return taskStatusHistoryUserExecutor;
	}

	public void setTaskStatusHistoryUserExecutor(Set<TaskStatusHistoryEntity> taskStatusHistoryUserExecutor) {
		this.taskStatusHistoryUserExecutor = taskStatusHistoryUserExecutor;
	}

	public Set<TaskTimeTrackingEntity> getTaskTimeTrackingUserAuthor() {
		return taskTimeTrackingUserAuthor;
	}

	public void setTaskTimeTrackingUserAuthor(Set<TaskTimeTrackingEntity> taskTimeTrackingUserAuthor) {
		this.taskTimeTrackingUserAuthor = taskTimeTrackingUserAuthor;
	}

	public Set<TaskTimeTrackingEntity> getTaskTimeTrackingUserAccepted() {
		return taskTimeTrackingUserAccepted;
	}

	public void setTaskTimeTrackingUserAccepted(Set<TaskTimeTrackingEntity> taskTimeTrackingUserAccepted) {
		this.taskTimeTrackingUserAccepted = taskTimeTrackingUserAccepted;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof UserEntity) && (id != null)
				? id.equals(((UserEntity) other).id)
						: (other == this);
	}

	@Override
	public int hashCode() {
		return (id != null)
				? (this.getClass().hashCode() + id.hashCode())
						: super.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s" + " " +"%s", firstName ,lastName);
	}
	
}
