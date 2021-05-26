package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BelongsToTB;

@Repository
public interface BelongsToTBRepository extends JpaRepository<BelongsToTB, Integer>{
	
	BelongsToTB findByBoardIdAndUserId(int a, int b);
	
	List<BelongsToTB> findByBoardId(int c);
	
	List<BelongsToTB> findByUserId(int c);
}
