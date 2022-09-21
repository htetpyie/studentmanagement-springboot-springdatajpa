package com.ace.studentmanagement_springdatajpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.studentmanagement_springdatajpa.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	Course findByName(String name);
}
