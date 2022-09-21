
  package com.ace.studentmanagement_springdatajpa.controller;
  
 import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
  
  import org.springframework.beans.factory.annotation.Autowired; import
  org.springframework.stereotype.Controller; import
  org.springframework.ui.ModelMap; import
  org.springframework.validation.BindingResult; import
  org.springframework.validation.annotation.Validated; import
  org.springframework.web.bind.annotation.GetMapping; import
  org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import
  org.springframework.web.bind.annotation.PostMapping; import
  org.springframework.web.bind.annotation.RequestParam; import
  org.springframework.web.servlet.ModelAndView;

import com.ace.studentmanagement_springdatajpa.dao.CourseService;
import com.ace.studentmanagement_springdatajpa.dao.ReportService;
import com.ace.studentmanagement_springdatajpa.dao.StudentService;
import com.ace.studentmanagement_springdatajpa.entity.Course;
import com.ace.studentmanagement_springdatajpa.entity.Student;
import com.ace.studentmanagement_springdatajpa.model.StudentBean;

import net.sf.jasperreports.engine.JRException;

  
  @Controller 
  public class StudentController {
  
	  @Autowired 
	  private StudentService studentService;
	  @Autowired
	  private CourseService courseService;
  
	  @GetMapping("/showStudentRegister") 
	  public ModelAndView showStudentRegister(HttpSession session) { 
		  if(session.getAttribute("user") == null) { 
			  return new ModelAndView("redirect:/"); 
			  }
		  else {	  
				  StudentBean studentBean = new StudentBean();
				  //studentBean.setStudentCourse("Java"); 
				  return new ModelAndView("STU001","studentBean",studentBean); 
				  } 
	 }
	  
	  @PostMapping("/studentRegister") 
	  public String studentRegister(@ModelAttribute("studentBean") @Validated StudentBean studentBean, BindingResult br, ModelMap model) { 
		  if(br.hasErrors()) { 
			  return "STU001"; 
			  } 
		  String id = studentService.idGenerator(); 
		  Student student = new Student(id, studentBean.getStudentName(), studentBean.getStudentDob(),studentBean.getStudentGender(), studentBean.getStudentPhone(),
				  			studentBean.getStudentEducation(),studentBean.getStudentPhoto()); 
		  ArrayList<Course> courses = studentBean.getStudentCourse();
		  for(Course c: courses) {
			  student.addCourse(c);
		  }
		  studentService.save(student);
		  //return "redirect:/showStudentAll";
		  model.addAttribute("success","Student Registered successfully.");
		  return "STU001";
		  }
	  
	  @GetMapping("/showStudentAll") 
	  public String showStudentAll(ModelMap model, HttpSession session) { 
		  if(session.getAttribute("user") == null) { 
			  return "redirect:/"; 
			  }
		  else 
			  {		  
			  List<Student> studentList = studentService.getAllStudent(); 
			  model.addAttribute("studentList", studentList); 
			  return "STU003"; 
			  } 
		}
	  
	  @GetMapping("/searchStudent") 
	  public String searchStudent(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("course") String course, ModelMap model) {
		  if(id.isBlank() && name.isBlank() && course.isBlank()) { 
			  return "redirect:/showStudentAll"; 
		  }else { 
			  List<Student> studentList = studentService.findStudentByIdOrNameOrCourses(id, name, course); 
		  model.addAttribute("studentList", studentList);
		  return "STU003"; 
		  } 
		 }
		  
	  @GetMapping("/seeMore/{id}") 
	  public ModelAndView seeMore(@PathVariable("id")String id) { 
		  Student student = studentService.getById(id);
		  Set<Course> attendedCourse = student.getCourses();
		  ArrayList<Course> list = new ArrayList<>();
		  for(Course c: attendedCourse) {
			  list.add(c);
		  }
		  StudentBean studentBean = new StudentBean(
				  student.getId(), 
				  student.getName(),
				  student.getDob(),
				  student.getGender(),
				  student.getPhone(),
				  student.getEducation(), 
				  student.getPhoto(),
				  list
				  );
		  return new  ModelAndView("STU002","studentBean", studentBean); }
	  
	  @GetMapping("/deleteStudent") 
	  public String deleteStudent(@RequestParam("id") String id) {
		  studentService.deleteById(id);
		  return "redirect:/showStudentAll"; }
		  
	  @GetMapping("/showStudentUpdate") 
	  public ModelAndView showStudentUpdate(@RequestParam("id") String id) {
		  Student student = studentService.getById(id);
		  ArrayList<Course> list = new ArrayList<>();
		  for(Course c: student.getCourses()) {
			  list.add(c);
		  }
		  StudentBean studentBean = new StudentBean(
				  student.getId(), 
				  student.getName(),
				  student.getDob(),
				  student.getGender(),
				  student.getPhone(),
				  student.getEducation(), 
				  student.getPhoto(),
				  list
				  );
		  return new ModelAndView("STU002-01","studentBean",studentBean); }
	  
	  @PostMapping("/updateStudent") 
	  public String updateStudent(@ModelAttribute("studentBean") @Validated StudentBean bean, BindingResult br) { 
		  if(br.hasErrors()) { return "STU002-01"; }	  
		  Student student = new Student(bean.getStudentId(), bean.getStudentName(), bean.getStudentDob(), bean.getStudentGender(), bean.getStudentPhone(), bean.getStudentEducation(), bean.getStudentPhoto() );
		  for(Course course: bean.getStudentCourse()) {
			  student.addCourse(course);
		  }
		  studentService.update(student);		  
		  return "redirect:/showStudentAll";		  
	  }
	  
	  @ModelAttribute(value="courseList") 
	  public List<Course> courseList(){
		  ArrayList<String> list = new ArrayList<>(); 
		  List<Course> courseLists= courseService.getAllCourse(); 
		  for(Course course: courseLists) { 
			  list.add(course.getName()); 
			  } 
		  return courseLists;	  
	  } 

	  @Autowired
	  private ReportService report;
	  @GetMapping("/report")
	  public String generateReport() throws FileNotFoundException, JRException{
		return report.exportReport("excel");
	  }
	  

 }
 