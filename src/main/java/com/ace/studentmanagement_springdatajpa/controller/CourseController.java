package com.ace.studentmanagement_springdatajpa.controller;
  
  
  import java.util.List;

import javax.servlet.http.HttpSession;
  
  import org.springframework.beans.factory.annotation.Autowired; import
  org.springframework.stereotype.Controller; import
  org.springframework.ui.ModelMap; import
  org.springframework.validation.BindingResult; import
  org.springframework.validation.annotation.Validated; import
  org.springframework.web.bind.annotation.GetMapping; import
  org.springframework.web.bind.annotation.ModelAttribute; import
  org.springframework.web.bind.annotation.PostMapping; import
  org.springframework.web.servlet.ModelAndView;

import com.ace.studentmanagement_springdatajpa.dao.CourseService;
import com.ace.studentmanagement_springdatajpa.entity.Course;
import com.ace.studentmanagement_springdatajpa.model.CourseBean;
  
  @Controller public class CourseController {
  
	  @Autowired 
	  private CourseService courseService;
	  
	  @GetMapping("/showCourseRegister") 
	  public ModelAndView showCourseRegister(HttpSession session) { 
		  if(session.getAttribute("user") == null) { 
			  return new ModelAndView("redirect:/"); 
			  }
		  else 
			  { CourseBean bean = new CourseBean(); 
			  return new ModelAndView("BUD003","courseBean",bean);
			  }
	  }
	  
	  @PostMapping("/courseRegister") 
	  public String courseRegister(@ModelAttribute("courseBean") @Validated CourseBean courseBean, BindingResult br, ModelMap model) { 
		  if(br.hasErrors()) { 
			  return "BUD003"; }
		  else if(courseService.isCourseExist(courseBean.getCourseName())) {
			  model.addAttribute("error",courseBean.getCourseName()+" already exists! ");
			  return "BUD003"; 
			  }
		  else { 
				  String courseName = courseBean.getCourseName();
				  Course course = new Course(courseName);
				  courseService.save(course);
				  model.addAttribute("success", courseName+" is successfully added!"); 
				  return "BUD003"; }
	  
	  }
	  
	 
//	  
//	  public  String idGenerator() { 
//		  String id = "";
//		  List<CourseResponseDTO> list = courseDAO.selectCourseAll(); 
//		  if(list == null || list.size() <= 0) { 
//			  id = "COU001"; 
//			  }else { 
//				  CourseResponseDTO lastDTO = list.get(list.size()-1); 
//				  int lastId = Integer.parseInt(lastDTO.getCourseId().substring(3)); 
//				  id = String.format("COU"+"%03d", lastId+1); 
//				  } 
//		  return id; 
//		  }
//		  
  }
 