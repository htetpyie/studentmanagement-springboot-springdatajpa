package com.ace.studentmanagement_springdatajpa.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.studentmanagement_springdatajpa.entity.Course;
@Service
public class CourseService {
	@Autowired 
	CourseRepository repo;
	
	public List<Course> getAllCourse(){
		return repo.findAll();
	}
	
	public Course getCourse(int id) {
		return repo.findById(id).get();
	}
	
	public void save(Course course) {
		repo.save(course);
	}
}
