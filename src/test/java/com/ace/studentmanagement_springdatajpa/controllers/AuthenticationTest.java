package com.ace.studentmanagement_springdatajpa.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ace.studentmanagement_springdatajpa.controller.Authentication;
import com.ace.studentmanagement_springdatajpa.controller.UserController;
import com.ace.studentmanagement_springdatajpa.dao.UserRepository;
import com.ace.studentmanagement_springdatajpa.dao.UserService;
import com.ace.studentmanagement_springdatajpa.entity.User;
import com.ace.studentmanagement_springdatajpa.model.UserBean;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserRepository repo;
	
	@MockBean
	UserService service;
	
	@Test
	public void displayLoginTest() throws Exception{
		this.mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"));
	}
	
	@Test
	public void showMenuIfTest() throws Exception{
		UserBean user = new UserBean("1","user","email","pass","role");
		this.mockMvc.perform(get("/showMenu").sessionAttr("user", user))
		.andExpect(status().isOk())
		.andExpect(view().name("MNU001"));
	}
	
	@Test
	public void showMenuElseTest() throws Exception{	
		this.mockMvc.perform(get("/showMenu"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void loginTestFail() throws Exception {
		this.mockMvc.perform(post("/Login").param("id","").param("password",""))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"))
		.andExpect(model().attributeExists("error"));
		
	}
	
	@Test
	public void loginTestOkay() throws Exception {
		when(service.selectUserByEmailAndPassword("email", "password")).thenReturn(new User(1,"name","email","password","role"));
		this.mockMvc.perform(post("/Login").param("id","email").param("password", "password"))
		.andExpect(status().isOk())
		.andExpect(view().name("MNU001"));
	}
	
	@Test
	public void loginTestFinal() throws Exception {
		//when(service.selectUserByEmailAndPassword("email", "password")).thenReturn(null);
		this.mockMvc.perform(post("/Login").param("id","email").param("password", "password"))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"));
	}

	@Test
	public void LogoutTest() throws Exception {
		this.mockMvc.perform(get("/Logout"))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"));
	}
	
	@Test
	public void dateTest() throws Exception{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY.MM.dd");
		LocalDateTime now = LocalDateTime.now();
		String date = dtf.format(now);
		assertEquals(date, Authentication.now());
	}

}
