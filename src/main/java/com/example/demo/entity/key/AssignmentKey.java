package com.example.demo.entity.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AssignmentKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9171771538268237722L;

	@Column(name="task_id")
	private int taskId;
	
	@Column(name="user_id")
	private int userId;
	
	public AssignmentKey() {
		
	}
	
	public AssignmentKey(int a, int b) {
		this.taskId = a;
		this.userId = b;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
