package com.ace.studentmanagement_springdatajpa.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

import com.ace.studentmanagement_springdatajpa.dao.StudentService;
import com.ace.studentmanagement_springdatajpa.entity.Course;
import com.ace.studentmanagement_springdatajpa.entity.Student;
import com.ace.studentmanagement_springdatajpa.model.StudentBean;
import com.ace.studentmanagement_springdatajpa.model.UserBean;

@SpringBootTest
@AutoConfigureMockMvc

public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	StudentService service;
	
	@Test
	public void showStudentRegisterSessionTest() throws Exception {
		this.mockMvc.perform(get("/showStudentRegister"))
		.andExpect(redirectedUrl("/"));
	}
	@Test
	public void showStudentRegisterTest() throws Exception {
		this.mockMvc.perform(get("/showStudentRegister").sessionAttr("user", new UserBean()))
		.andExpect(view().name("STU001"))
		.andExpect(model().attributeExists("studentBean"));
	}
	
	@Test
	public void studentRegisterValidate() throws Exception {
		this.mockMvc.perform(post("/studentRegister"))
		.andExpect(view().name("STU001"));
	}
	
	@Test
	public void studentRegister() throws Exception {
		when(service.idGenerator()).thenReturn("STU001");
		StudentBean student = new StudentBean("STU001","Mg Mg", "dob", "gender", "phone", "Education","photo");
		ArrayList<Course> course = new ArrayList<>();
		Course c1 = new Course(1,"Java");
		Course c2 = new Course(2,"Python");
		course.add(c1);course.add(c2);
		student.setStudentCourse(course);
		this.mockMvc.perform(post("/studentRegister").flashAttr("studentBean", student))
		.andExpect(view().name("STU001"));
		//.andExpect(model().attributeExists("success"));
	}
	
	@Test
	public void showStudentAllSessionTest() throws Exception{
		this.mockMvc.perform(get("/showStudentAll"))
		.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void showStudentAllTest() throws Exception{
		List<Student> list = new ArrayList<>();
		when(service.getAllStudent()).thenReturn(list);
		this.mockMvc.perform(get("/showStudentAll").sessionAttr("user", new UserBean()))
		.andExpect(view().name("STU003"));
	}
	
	@Test
	public void searchStudentAllBlankTest() throws Exception{
		this.mockMvc.perform(get("/searchStudent").param("id", "").param("name", "").param("course", ""))
		.andExpect(redirectedUrl("/showStudentAll"));
	}
	
	@Test
	public void searchStudentTest() throws Exception{
		List<Student> list = new ArrayList<>();
		when(service.findStudentByIdOrNameOrCourses("STU001", "Mg Mg", "Java")).thenReturn(list);
		this.mockMvc.perform(get("/searchStudent").param("id", "STU001").param("name", "").param("course", ""))
		.andExpect(view().name("STU003"));
	}
	
	@Test
	public void seeMoreTest() throws Exception {
		Student student = new Student("STU001","Mg Mg", "dob", "gender", "phone", "Education","photo");
		when(service.getById("STU001")).thenReturn(new Student());
		Set<Course> courses = new HashSet<>();
		Course c1 = new Course(1,"Java");
		Course c2 = new Course(2,"Python");
		courses.add(c1);
		courses.add(c2);
		student.setCourses(courses);	
		ArrayList<Course> list = new ArrayList<>();
		for(Course c : courses) {
			list.add(c);
		}
		this.mockMvc.perform(get("/seeMore/STU001"))
		.andExpect(view().name("STU002"))
		.andExpect(model().attributeExists("studentBean"));
	}
	
	@Test
	public void deleteStudentTest() throws Exception{
		this.mockMvc.perform(get("/deleteStudent").param("id", "STU001"))
		.andExpect(redirectedUrl("/showStudentAll"));
	}
	
	@Test
	public void showStudentUpdateTest() throws Exception{
		when(service.getById("STU001")).thenReturn(new Student());
		Course c1 = new Course(1,"Java");
		Course c2 = new Course(2,"Python");	
		ArrayList<Course> list = new ArrayList<>();
		list.add(c1); list.add(c2);
		this.mockMvc.perform(get("/showStudentUpdate").param("id", "STU001"))
		.andExpect(view().name("STU002-01"))
		.andExpect(model().attributeExists("studentBean"));
	}
	
	@Test
	public void updateStudentValidateTest() throws Exception {
		this.mockMvc.perform(post("/updateStudent"))
		.andExpect(view().name("STU002-01"));
	}
	@Test
	public void updateStudentTest() throws Exception {
		StudentBean student = new StudentBean("STU001","Mg Mg", "dob", "gender", "phone", "Education","photo");
		ArrayList<Course> courses = new ArrayList<Course>();
		Course c1 = new Course(1,"Java");
		Course c2 = new Course(2,"Python");
		courses.add(c1);
		courses.add(c2);
		student.setStudentCourse(courses);	
		this.mockMvc.perform(post("/updateStudent").flashAttr("studentBean", student))
		.andExpect(redirectedUrl("/showStudentAll"));
	}
}
