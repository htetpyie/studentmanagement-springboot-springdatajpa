package com.ace.studentmanagement_springdatajpa.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.studentmanagement_springdatajpa.entity.User;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	public List<User> getAllUser(){
		return userRepo.findAll();
	}
	
	public User getUser(int id) {
		return userRepo.findById(id).get();
	}
	
	public void save(User user) {
		userRepo.save(user);
	}
	
	public void delete(int id) {
		userRepo.deleteById(id);
	}
	
	public void update(User user) {
		userRepo.save(user);
	}
	
	public List<User> selectUsersByIdAndName(int id, String name){
		return userRepo.findDintinctUserByIdOrName(id, name);
	}
	
	public User selectUserByEmailAndPassword(String email, String password) {
		return userRepo.findUserByEmailAndPassword(email, password);
	}
	public User selectUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	 public boolean isEmailExist(String email) { 
		 User user = selectUserByEmail(email);
		  if(user == null) { 
			 return false;
		  } 					  
	  return true; 
	  }	
}
