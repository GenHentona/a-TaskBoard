package com.example.demo.entity.key;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BelongsKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5598855235547862210L;

	@Column(name="board_id")
	private int boardId;
	
	@Column(name="user_id")
	private int userId;
	
	public BelongsKey() {
		
	}
	
	public BelongsKey(int boardId, int userId) {
		this.boardId = boardId;
		this.userId = userId;
	}

	public Integer getBoardId() {
		return boardId;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}