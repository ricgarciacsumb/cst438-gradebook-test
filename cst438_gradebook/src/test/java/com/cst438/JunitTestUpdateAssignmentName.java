package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.controllers.AssignmentController;
import com.cst438.domain.Assignment;
//import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentListDTO;

import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.services.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { AssignmentController.class })
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
public class JunitTestUpdateAssignmentName 
{
	static final String URL = "http://localhost:8080";
	public static final int TEST_COURSE_ID = 40442;
	public static final String TEST_STUDENT_EMAIL = "test@csumb.edu";
	public static final String TEST_STUDENT_NAME = "test";
	public static final String TEST_INSTRUCTOR_EMAIL = "dwisneski@csumb.edu";
	public static final int TEST_YEAR = 2021;
	public static final String TEST_SEMESTER = "Fall";

	@MockBean
	AssignmentRepository assignmentRepository;

	@MockBean
	AssignmentGradeRepository assignmentGradeRepository;

	@MockBean
	CourseRepository courseRepository; // must have this to keep Spring test happy

	@MockBean
	RegistrationService registrationService; // must have this to keep Spring test happy
	@Autowired
	private MockMvc mvc;
	
    @Test
    public void testChangeAssignmentName() throws Exception 
	{
    	MockHttpServletResponse response;
    	
		// assign assignment
    	Assignment assign1 = new Assignment();
    	AssignmentListDTO.AssignmentDTO assign = new AssignmentListDTO.AssignmentDTO(1, 0, null, null, null);
		//Course course = new Course();
		assign.courseId = 2;
		//course.getAssignments().add(assign);
		assign.assignmentName ="Assignment 2";
        
        given(assignmentRepository.findById(1)).willReturn(Optional.of(assign1));
        
//        Assignment savedAssignment = new Assignment();
//        savedAssignment = assign1;

        //assertEquals(assign1, savedAssignment); //verify step
        
        response = mvc.perform(MockMvcRequestBuilders.put("/assignment").accept(MediaType.APPLICATION_JSON)
						.content(asJsonString(assign)).contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
        
        assertEquals(200, response.getStatus());

		
    }
    private static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T fromJsonString(String str, Class<T> valueType) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
