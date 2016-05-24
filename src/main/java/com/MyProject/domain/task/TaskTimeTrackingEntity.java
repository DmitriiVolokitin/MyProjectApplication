
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

import com.MyProject.domain.commons.FormatTimeTrackingEntity;
import com.MyProject.domain.user.UserEntity;

@Entity
@Table(name="task_time_tracking")
public class TaskTimeTrackingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_time_tracking_ID", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="task_ID")
	private TaskEntity task;

	@Column(name="task_time_tracking_Date", nullable = false)
	private Date date;

	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="userAuthor_ID")
	private UserEntity userAuthor;

	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="formatTimeTracking_ID")
	private FormatTimeTrackingEntity formatTimeTracking;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="userAccepted_ID")
	private UserEntity userAccepted;

	@Column(name="task_time_tracking_start_Time", nullable = false)
	private double startTime = 0.00;

	@Column(name="task_time_tracking_finish_Time", nullable = false)
	private double finishTime = 0.00;

	@Column(name="task_time_tracking_actual_Time", nullable = false)
	private double actualTime = 0.00;

	@Column(name="task_time_tracking_description", length = 255, nullable = false)
	private String description;

	@Column(name="task_time_tracking_accepted", nullable = true)
	private Boolean accepted = false;

	@Column(name="task_time_tracking_date_Accepted", nullable = true)
	private Date dateAccepted;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UserEntity getUserAuthor() {
		return userAuthor;
	}

	public void setUserAuthor(UserEntity userAuthor) {
		this.userAuthor = userAuthor;
	}

	public FormatTimeTrackingEntity getFormatTimeTracking() {
		return formatTimeTracking;
	}

	public void setFormatTimeTracking(FormatTimeTrackingEntity formatTimeTracking) {
		this.formatTimeTracking = formatTimeTracking;
	}

	public double getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(double finishTime) {
		this.finishTime = finishTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public UserEntity getUserAccepted() {
		return userAccepted;
	}

	public void setUserAccepted(UserEntity userAccepted) {
		this.userAccepted = userAccepted;
	}

	public Date getDateAccepted() {
		return dateAccepted;
	}

	public void setDateAccepted(Date dateAccepted) {
		this.dateAccepted = dateAccepted;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getActualTime() {
		return actualTime;
	}

	public void setActualTime(double actualTime) {
		this.actualTime = actualTime;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof TaskTimeTrackingEntity) && (id != null)
				? id.equals(((TaskTimeTrackingEntity) other).id)
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