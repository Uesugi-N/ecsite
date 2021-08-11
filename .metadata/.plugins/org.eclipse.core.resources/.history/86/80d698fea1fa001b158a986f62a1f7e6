package com.portfolio.model.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.portfolio.model.entity.User;

public interface RegisterRepository extends JpaRepository<User, Long>{
	
	@Query(value="INSERT INTO user (id, user_name, password, full_name, is_admin)" +
			"VALUES (?1,?2,?3,?4,?5)",
			nativeQuery= true)
	
	@Transactional
	@Modifying
	void persist(
			@Param("id") long id,
			@Param("userName") String userName,
			@Param("password") String password,
			@Param("fullName") String fullName,
			@Param("isAdmin") long isAdmin);
}
