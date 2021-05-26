package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query(value = "select exists(select password from users where login_id = ?1)", nativeQuery=true)
	  boolean checkID(String loginId) ;

	User findById(int id);

	User findByLoginId(String loginId);
}
