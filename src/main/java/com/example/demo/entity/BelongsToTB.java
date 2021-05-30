package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.entity.key.BelongsKey;


@Entity
@Table(name="belong_boards")
public class BelongsToTB {
	
	@EmbeddedId
	private BelongsKey key;
	
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
	@JoinColumn(name="board_id", referencedColumnName = "id", insertable = false, updatable = false)
	private TaskBoard board;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	public BelongsToTB() {
		
	}
	
	

	public BelongsToTB(int boardId, int userId) {
		this.key = new BelongsKey(boardId, userId);
		this.isDeleted = 0;
		this.createdAt = new Timestamp(System.currentTimeMillis());
		this.createdBy = 0;
		this.updatedAt = new Timestamp(System.currentTimeMillis());
		this.updatedBy = 0;
	}
	
	


	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public TaskBoard getBoard() {
		return board;
	}



	public void setBoard(TaskBoard board) {
		this.board = board;
	}



	public BelongsKey getKey() {
		return key;
	}



	public void setKey(BelongsKey key) {
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
