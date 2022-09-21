package com.ace.studentmanagement_springdatajpa.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ace.studentmanagement_springdatajpa.controller.CourseController;
import com.ace.studentmanagement_springdatajpa.dao.CourseService;
import com.ace.studentmanagement_springdatajpa.entity.Course;
import com.ace.studentmanagement_springdatajpa.entity.User;
import com.ace.studentmanagement_springdatajpa.model.CourseBean;
import com.ace.studentmanagement_springdatajpa.model.UserBean;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	CourseService service;
	
	@Test
	public void TestshowCourseRegisterIfTest() throws Exception{
		this.mockMvc.perform(get("/showCourseRegister"))
		.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void TestshowCourseRegisterElseTest() throws Exception{
		this.mockMvc.perform(get("/showCourseRegister").sessionAttr("user", new UserBean("1","name","email","pass","role")))
		.andExpect(view().name("BUD003"))
		.andExpect(model().attributeExists("courseBean"));
		
	}
	
	@Test
	public void TestCourseRegisterValidate() throws Exception{
		this.mockMvc.perform(post("/courseRegister"))
		.andExpect(view().name("BUD003"));
	}
	
	@Test
	public void courseRegisterCourseExistTest() throws Exception{
		CourseBean courseBean = new CourseBean();
		courseBean.setCourseName("Java");
		courseBean.setCourseId("1");
		when(service.isCourseExist("Java")).thenReturn(true);
		this.mockMvc.perform(post("/courseRegister").flashAttr("courseBean", courseBean))
		.andExpect(view().name("BUD003"));
	}
	
	@Test
	public void courseRegisterOkayTest() throws Exception{
		CourseBean courseBean = new CourseBean();
		courseBean.setCourseName("Java");
		courseBean.setCourseId("1");
		when(service.isCourseExist("Java")).thenReturn(false);
		this.mockMvc.perform(post("/courseRegister").flashAttr("courseBean", courseBean))
		.andExpect(view().name("BUD003"));
	}
	
}
