package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.entity.key.AssignmentKey;

@Entity
@Table(name="assignment_tasks")
public class AssignmentTasks {
	@EmbeddedId
	private AssignmentKey key;
	
	@Column(name="is_deleted")
	private Integer isDeleted;
	
	@Column(name="created_at")
	private Timestamp createdAt;
	
	@Column(name="created_by")
	private int createdBy = 0;
	
	@Column(name="updated_at")
	private Timestamp updatedAt;
	
	@Column(name="updated_by")
	private int updatedBy = 0;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="task_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Task task;
	
	
	public AssignmentTasks(int taskId, int userId) {
		this.key = new AssignmentKey(taskId, userId);
		this.isDeleted = 0;
		this.createdAt = new Timestamp(System.currentTimeMillis());
		this.createdBy = 0;
		this.updatedAt = new Timestamp(System.currentTimeMillis());
		this.updatedBy = 0;
	}
	
	public AssignmentTasks() {
		
	}

	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public AssignmentKey getKey() {
		return key;
	}

	public void setKey(AssignmentKey key) {
		this.key = key;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
}
