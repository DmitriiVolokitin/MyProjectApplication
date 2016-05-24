
package com.MyProject.domain.task;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.MyProject.domain.user.UserEntity;

@Entity
@Table(name="task_status_histories")
public class TaskStatusHistoryEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_status_history_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="task_ID")
	private TaskEntity task;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="userAuthor_ID")
	private UserEntity userAuthor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="userExecutor_ID")
	private UserEntity userExecutor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="status_ID")
	private TaskStatusEntity status;
	
	@Column(name="task_status_history_Date", nullable = false)
	private Date date;
	
	@Column(name="task_status_history_Comment", length = 255, nullable = true)
	private String comment;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TaskEntity getTask() {
		return task;
	}

	public void setTask(TaskEntity task) {
		this.task = task;
	}

	public UserEntity getUserAuthor() {
		return userAuthor;
	}

	public void setUserAuthor(UserEntity userAuthor) {
		this.userAuthor = userAuthor;
	}

	public UserEntity getUserExecutor() {
		return userExecutor;
	}

	public void setUserExecutor(UserEntity userExecutor) {
		this.userExecutor = userExecutor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public TaskStatusEntity getStatus() {
		return status;
	}

	public void setStatus(TaskStatusEntity status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof TaskStatusHistoryEntity) && (id != null)
				? id.equals(((TaskStatusHistoryEntity) other).id)
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
		return String.format("%d", id);
	}

	
}