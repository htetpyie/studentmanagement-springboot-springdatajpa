package com.ace.studentmanagement_springdatajpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.studentmanagement_springdatajpa.dao.UserRepository;
import com.ace.studentmanagement_springdatajpa.dao.UserService;
import com.ace.studentmanagement_springdatajpa.entity.User;

@SpringBootTest
public class UserServiceTest {
	
	@Mock
	UserRepository repo;
	
	@InjectMocks
	UserService service;
	
	@Test
	public void getAllUserTest() {
		List<User> list = new ArrayList<>();
		User user1 = new User(1,"Mg", "mg", "password", "role1");
		User user2 = new User(2,"Mg2", "mg2", "password2", "role2");		
		list.add(user1);
		list.add(user2);
		when(repo.findAll()).thenReturn(list);
		List<User> userList = service.getAllUser();
		verify(repo, times(1)).findAll();
		assertEquals(2,userList.size());
	}
	
	@Test
	public void getUserTest() {
		User u = new User(1, "aung","222@gmail.com", "13","3");
		when(repo.findById(1)).thenReturn(Optional.ofNullable(u));
		User user = service.getUser(1);
		assertEquals("aung",user.getName());
		assertEquals("222@gmail.com",user.getEmail());
		
	}
	
	@Test
	public void saveTest() {
		User u = new User(1, "aung","222@gmail.com", "13","3");
		service.save(u);
		verify(repo, times(1)).save(u);
	}
	
	@Test
	public void deleteTest() {
		service.delete(1);
		verify(repo,times(1)).deleteById(1);
	}
	
	@Test
	public void updateTest() {
		User user = new User(1, "name", "email", "password", "role");
		service.update(user);
		verify(repo, times(1)).save(user);
	}
	
	@Test
	public void selectUsersByIdAndNameTest() {
		List<User> list = new ArrayList<>();
		User user1 = new User(1, "name", "email", "password", "role");
		User user2 = new User(2, "name2", "email2", "password2", "role2");
		list.add(user1);
		list.add(user2);
		when(repo.findDintinctUserByIdOrName(1, "name")).thenReturn(list);
		List<User> result = service.selectUsersByIdAndName(1, "name");
		assertEquals(2, result.size());
		
	}
	
	@Test
	public void selectUserByEmailAndPasswordTest() {
		List<User> list = new ArrayList<>();
		User user = new User(1, "name", "email", "password", "role");
		when(repo.findUserByEmailAndPassword("email", "password")).thenReturn(user);
		User result = service.selectUserByEmailAndPassword("email", "password");
		assertEquals(1,result.getId());
	}
	
	@Test
	public void selectUserByEmailTest() {
		User user = new User(1, "name", "email", "password", "role");
		when(repo.findByEmail("email")).thenReturn(user);
		User result = service.selectUserByEmail("email");
		assertEquals(1, result.getId());
	}
	
	@Test
	public void isEmailExistTest() {
		User user = new User(1, "name", "email", "password", "role");
		when(repo.findByEmail("email")).thenReturn(user);
		assertEquals(true, service.isEmailExist("email"));
		assertEquals(false, service.isEmailExist("email2"));
	}

}
