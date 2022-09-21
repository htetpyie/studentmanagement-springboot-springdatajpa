package com.ace.studentmanagement_springdatajpa.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ace.studentmanagement_springdatajpa.dao.StudentRepository;
import com.ace.studentmanagement_springdatajpa.dao.StudentService;
import com.ace.studentmanagement_springdatajpa.entity.Course;
import com.ace.studentmanagement_springdatajpa.entity.Student;

@SpringBootTest
public class StudentServiceTest {
	
	@Mock
	StudentRepository repo;
	
	@InjectMocks
	StudentService service;
	
	@Test
	public void saveTest() {
		Student s = new Student("001","Mg Mg","12/1/199","male","+988822","UCSY","");
		service.save(s);
		verify(repo, times(1)).save(s);
	}
	
	@Test
	public void getByIdTest() {
		Student s =  new Student("001","Mg Mg","12/1/199","male","+988822","UCSY","photo");
		when(repo.findById("001")).thenReturn(Optional.ofNullable(s));
		Student stu = service.getById("001");
		assertEquals("Mg Mg", stu.getName());
		assertEquals("12/1/199",stu.getDob());
		assertEquals("male",stu.getGender());
		assertEquals("+988822",stu.getPhone());
		assertEquals("UCSY",stu.getEducation());
	}
	
	@Test
	public void getAllStudentTest() {
		List<Student> list = new ArrayList<>();
		Student s =  new Student("001","Mg Mg","12/1/199","male","+988822","UCSY","photo");
		Student s1 =  new Student("002","Mg Mg","12/1/199","male","+988822","UCSY","photo");
		Student s2 =  new Student("003","Mg Mg","12/1/199","male","+988822","UCSY","photo");
		list.add(s);
		list.add(s1);
		list.add(s2);
		when(repo.findAll()).thenReturn(list);
		List<Student> l = service.getAllStudent();
		assertEquals(3, l.size());
	}
	
	@Test
	public void updateTest() {
		Student s =  new Student("001","Mg Mg","12/1/199","male","+988822","UCSY","photo");
		service.update(s);
		verify(repo,times(1)).save(s);
	}
	
	@Test
	public void deleteByIdTest() {
		Student s =  new Student("001","Mg Mg","12/1/199","male","+988822","UCSY","photo");
		service.deleteById("001");
		verify(repo,times(1)).deleteById("001");
	}
	
	@Test
	public void findStudentByIdOrNameOrCoursesTest() {
		List<Student> list = new ArrayList<>();
		Student s =  new Student("001","Mg Mg","12/1/199","male","+988822","UCSY","photo");
		Student s1 =  new Student("002","Aung Aung","12/1/199","male","+988822","UCSY","photo");
		Student s2 =  new Student("003","Mg Mg","12/1/199","male","+988822","UCSY","photo");
		Course course1 = new Course(1,"Java");
		Course course2 = new Course(2,"C#");
		Course course3 = new Course(3,"Python");
		list.add(s);
		list.add(s1);
		list.add(s2);
		s.addCourse(course1);
		s.addCourse(course2);
		when(repo.findDistinctStudentByIdOrNameOrCourses_Name("003", "Aung Aung", "Java")).thenReturn(list);
		List<Student> l = service.findStudentByIdOrNameOrCourses("003", "Aung Aung", "Java");
		assertEquals(3, l.size());
	}
	
	@Test
	public void idGeneratorIfTest() {
		String id = "STU001";
		assertEquals(id, service.idGenerator());
	}
	
	@Test
	public void idGeneratorElseTest() {
		List<Student> list = new ArrayList<>();
		Student s =  new Student("STU001","Mg Mg","12/1/199","male","+988822","UCSY","photo");
		Student s1 =  new Student("STU002","Mg Mg","12/1/199","male","+988822","UCSY","photo");
		list.add(s);
		list.add(s1);
		when(repo.findAll()).thenReturn(list);
		assertEquals("STU003", service.idGenerator());
	}
}
	
