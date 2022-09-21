package com.ace.studentmanagement_springdatajpa.controllers;

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

import com.ace.studentmanagement_springdatajpa.dao.UserRepository;
import com.ace.studentmanagement_springdatajpa.dao.UserService;
import com.ace.studentmanagement_springdatajpa.entity.User;
import com.ace.studentmanagement_springdatajpa.model.UserBean;

@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserRepository repo;
	
	@MockBean
	UserService service;
	
	@Test
	public void TestDisplayUserRegister() throws Exception{
		UserBean user = new UserBean();
		this.mockMvc.perform(get("/displayUserRegister"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"))
		.andExpect(model().attributeExists("userBean"));
	}
	
	@Test
	public void TestUserRegisterValidate() throws Exception{
		this.mockMvc.perform(post("/userRegister"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));	
	}
	
	@Test 
	public void TestUserRegisterPasswordNotEqual() throws Exception{
		UserBean user = new UserBean("1","Name", "email", "password", "role");
		user.setUserCfPassword("a");
		this.mockMvc.perform( post("/userRegister").flashAttr("userBean", user))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));
	}
	
	@Test
	public void TestUserRegisterEmailExist() throws Exception{
		UserBean user = new UserBean("1","Name", "email", "password", "role");
		user.setUserCfPassword("password");
		when(service.isEmailExist("email")).thenReturn(true);
		this.mockMvc.perform( post("/userRegister").flashAttr("userBean", user))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));
	}
	
	@Test 
	public void TestUserRegisterOk() throws Exception{
		UserBean userBean = new UserBean("1","Name", "email", "password", "role");
		userBean.setUserCfPassword("password");
		this.mockMvc.perform( post("/userRegister").flashAttr("userBean", userBean))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));
		
	}
	
	@Test
	public void TestShowUserElse() throws Exception {
		UserBean user = new UserBean("1","mg","mail","pas");
		this.mockMvc.perform(get("/showUser").sessionAttr("user", user))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"))
		.andExpect(model().attributeExists("userList"));
	}
	
	@Test
	public void TestShowUserIf() throws Exception {
		List<User> list = new ArrayList<>();
		when(repo.findAll()).thenReturn(list);
		this.mockMvc.perform(get("/showUser"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void TestShowAddUser() throws Exception {
		UserBean user = new UserBean();
		this.mockMvc.perform(get("/showAddUser"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001-01"))
		.andExpect(model().attributeExists("userBean"));
	}
	
	@Test
	public void TestUserAddValidate() throws Exception {
		this.mockMvc.perform(post("/userAdd"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001-01"));
	}
	
	@Test 
	public void TestUserAddPasswordNotEqual() throws Exception{
		UserBean user = new UserBean("1","Name", "email", "password", "role");
		user.setUserCfPassword("a");
		this.mockMvc.perform( post("/userAdd").flashAttr("userBean", user))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001-01"));
	}
	
	@Test
	public void TestAddEmailExist() throws Exception{
		UserBean user = new UserBean("1","Name", "email", "password", "role");
		user.setUserCfPassword("password");
		when(service.isEmailExist("email")).thenReturn(true);
		this.mockMvc.perform( post("/userAdd").flashAttr("userBean", user))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001-01"));
	}

	
	@Test 
	public void TestUserAddOk() throws Exception{
		UserBean userBean = new UserBean("1","Name", "email", "password", "role");
		userBean.setUserCfPassword("password");
		this.mockMvc.perform( post("/userAdd").flashAttr("userBean", userBean))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/showUser"));
	}
	
	@Test
	public void TestUserSearchNoParameter() throws Exception {
		this.mockMvc.perform(get("/userSearch").param("id", "").param("name", ""))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/showUser"));
	}
	@Test
	public void TestUserSearchWithParameter() throws Exception {
		this.mockMvc.perform(get("/userSearch").param("id", "").param("name", "Name"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"));
	}
	@Test
	public void TestUserSearchWithParameter2() throws Exception {
		this.mockMvc.perform(get("/userSearch").param("id", "1").param("name", "Name"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"));
	}
	
	@Test
	public void TestShowUserUpdate() throws Exception {
		when(service.getUser(1)).thenReturn(new User());		
		this.mockMvc.perform(get("/showUserUpdate").param("id", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"))
		.andExpect(model().attributeExists("userBean"));
	}
	
	@Test
	public void TestUserUpdateValidate() throws Exception{
		when(service.getUser(1)).thenReturn(new User(1,"Name","email","password","role"));
		this.mockMvc.perform(post("/userUpdate").flashAttr("userBean", new UserBean("1", "mg mg","mg@gmail.com","13")))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"));
	}
	@Test
	public void TestUserUpdatePasswordValidate() throws Exception{
		when(service.getUser(1)).thenReturn(new User(1,"Name","email","password","role"));
		UserBean userBean =  new UserBean("1", "mg mg","mg@gmail.com","13","role");
		userBean.setUserCfPassword("11");
		this.mockMvc.perform(post("/userUpdate").flashAttr("userBean",userBean))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"));
	}
	
	@Test
	public void TestUpdateEmailExist() throws Exception{
		UserBean user = new UserBean("1","Name", "email", "password", "role");
		user.setUserCfPassword("password");
		when(service.isEmailExist("email")).thenReturn(true);
		when(service.getUser(1)).thenReturn(new User(1,"Name", "differentemail", "password", "role"));
		this.mockMvc.perform( post("/userUpdate").flashAttr("userBean", user))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"));
	}
	
	@Test
	public void TestUserUpdaeOk() throws Exception{
		when(service.getUser(1)).thenReturn(new User(1,"Name","email","password","role"));
		UserBean userBean =  new UserBean("1", "mg mg","mg@gmail.com","13","role");
		userBean.setUserCfPassword("13");
		this.mockMvc.perform(post("/userUpdate").flashAttr("userBean",userBean))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/showUser"));
	}
	
	@Test
	public void TestUserDelete() throws Exception{
		this.mockMvc.perform(get("/userDelete").param("id", "1"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/showUser"));
	}
	
}
