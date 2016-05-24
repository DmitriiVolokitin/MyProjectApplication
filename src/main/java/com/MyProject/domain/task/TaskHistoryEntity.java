
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
@Table(name="task_histories")
public class TaskHistoryEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_history_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="task_ID")
	private TaskEntity task;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="changeAuthor_ID")
	private UserEntity changeAuthor;
	
	@Column(name="task_history_ChangeDate", nullable = false)
	private Date changeDate;
	
	@Column(name="task_history_ChangeEvent", nullable = false)
	private String changeEvent;
	
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

	public String getChangeEvent() {
		return changeEvent;
	}

	public void setChangeEvent(String changeEvent) {
		this.changeEvent = changeEvent;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public UserEntity getChangeAuthor() {
		return changeAuthor;
	}

	public void setChangeAuthor(UserEntity changeAuthor) {
		this.changeAuthor = changeAuthor;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof TaskHistoryEntity) && (id != null)
				? id.equals(((TaskHistoryEntity) other).id)
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