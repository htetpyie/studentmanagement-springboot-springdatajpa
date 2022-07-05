package com.ace.studentmanagement_springdatajpa.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.studentmanagement_springdatajpa.entity.Student;

@Service
public class StudentService {
	@Autowired
	StudentRepository repo;
	
	public void save(Student s) {
		repo.save(s);
	}
	
	public Student getById(String id) {
		return repo.findById(id).get();
	}
	
	public List<Student> getAllStudent(){
		return repo.findAll();
	}
	
	public void update(Student s) {
		repo.save(s);
	}
	
	public void deleteById(String id) {
		repo.deleteById(id);
	}
	
	public List<Student> findStudentByIdOrNameOrCourses(String id, String name, String course){
		return repo.findDistinctStudentByIdOrNameOrCourses_Name(id, name, course);
	}

	  public  String idGenerator() { 
		  String id = ""; 
		  List<Student> list = getAllStudent();
		  if(list == null || list.size() <= 0) { 
			  id = "STU001"; 
		  }else {
			  Student lastDTO = list.get(list.size()-1); 
			  int lastId = Integer.parseInt(lastDTO.getId().substring(3)); 
			  id = String.format("STU"+"%03d", lastId+1); 
			  } 
		  return id; 
		  }
}
