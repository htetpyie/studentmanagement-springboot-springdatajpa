package com.ace.studentmanagement_springdatajpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.studentmanagement_springdatajpa.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String>{
	
	List<Student> findDistinctStudentByIdOrNameOrCourses_Name(String id, String name, String course);
		
	
}
