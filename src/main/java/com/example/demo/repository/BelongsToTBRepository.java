package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BelongsToTB;
import com.example.demo.entity.key.BelongsKey;

@Repository
public interface BelongsToTBRepository extends JpaRepository<BelongsToTB, BelongsKey>{
	
	public List<BelongsToTB> findByKeyUserId(int userId);
	public List<BelongsToTB> findByKeyBoardIdAndIsDeleted(int boardId, int isDeleted);
	
	BelongsToTB findByKeyBoardIdAndKeyUserId(int boardId, int userId);
	public List<BelongsToTB> findByKeyUserIdAndIsDeleted(Integer id, int i);
}
