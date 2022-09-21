package com.ace.studentmanagement_springdatajpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.studentmanagement_springdatajpa.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findDintinctUserByIdOrName(int id, String name);

	User findUserByEmailAndPassword(String email, String password);
	
	User findByEmail(String email);
}
