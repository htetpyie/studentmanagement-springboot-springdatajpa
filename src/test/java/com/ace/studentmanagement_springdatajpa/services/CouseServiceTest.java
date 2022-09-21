package com.ace.studentmanagement_springdatajpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.studentmanagement_springdatajpa.dao.CourseRepository;
import com.ace.studentmanagement_springdatajpa.dao.CourseService;
import com.ace.studentmanagement_springdatajpa.entity.Course;

@SpringBootTest
public class CouseServiceTest {

	@Mock
	CourseRepository repo;
	
	@InjectMocks
	CourseService service;
	
	@Test
	public void getAllCourseTest() {
		List<Course> list = new ArrayList<>();
		Course c = new Course(1, "course1");
		Course c1 = new Course(2,"course2");
		list.add(c);
		list.add(c1);
		when(repo.findAll()).thenReturn(list);
		List<Course> courseList = service.getAllCourse();
		assertEquals(2,courseList.size());
	}
	
	@Test
	public void getCourseTest() {
		Course c = new Course(1, "Java");
		when(repo.findById(1)).thenReturn(Optional.ofNullable(c));
		Course course = service.getCourse(1);
		assertEquals("Java", course.getName());
	}
	
	@Test
	public void saveTest() {
		Course c = new Course(1, "Java");
		service.save(c);
		verify(repo, times(1)).save(c);
	}
	
	@Test
	public void findByNameTest() {
		Course c = new Course(1,"Java");
		when(repo.findByName("Java")).thenReturn(c);
		Course course = service.findByCoureName("Java");
		assertEquals("Java", course.getName());
	}
	
	@Test
	public void emailExistsTest() {
		Course c = new Course(1,"Java");
		when(repo.findByName("Java")).thenReturn(c);
		Course course = service.findByCoureName("Java");
		assertEquals("Java", course.getName());
		assertEquals(true, service.isCourseExist("Java"));
		assertEquals(false, service.isCourseExist("Python"));
	}
	
//	@Test
//	public void emailExistsTestFalse() {
//		Course c = new Course(1,"Java");
//		when(repo.findByName("Java")).thenReturn(c);
//		Course course = service.findByCoureName("Java");
//		assertEquals("Java", course.getName());
//		assertEquals(fa, service.isCourseExist("Java"));
//	}
	
}
