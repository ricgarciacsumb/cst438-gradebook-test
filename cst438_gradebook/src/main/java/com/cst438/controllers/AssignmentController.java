package com.cst438.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentListDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;

//import java.util.Arraylist;

@RestController 
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class AssignmentController 
{
	@Autowired
	AssignmentRepository assignmentRepository;
	@Autowired
	CourseRepository courseRepository;

	@PostMapping("/assignment")
	public int addAssignment(@RequestBody AssignmentListDTO.AssignmentDTO assignment) throws Exception{
		//checks name and due date
		Assignment a = new Assignment();
		a.setName(assignment.assignmentName);
		a.setNeedsGrading(1);
		a.setDueDate(java.sql.Date.valueOf(assignment.dueDate));
		Course c = courseRepository.findById(assignment.courseId).orElse(null);
		if (c == null)
			throw new Exception("Course Does not Exists!!");
		a.setCourse(c);
		assignmentRepository.save(a);
		return a.getId();
	
	}
	
	@PutMapping("/assignment")
	public void updateAssignmentName( @RequestBody AssignmentListDTO.AssignmentDTO assignment) throws Exception{
	    // Retrieve the assignment from the database using the assignmentId
	    Assignment x = assignmentRepository.findById(assignment.assignmentId).orElse(null);
	    if (x == null)
			throw new Exception("Did not Update!!");
	    // Update the assignment name with the new name from the request
	    x.setName(assignment.assignmentName);

	    // Save the updated assignment
	    assignmentRepository.save(x);

	}
	@DeleteMapping("/assignment/{id}")
	public void deleteAssignment( @PathVariable("id") int id) throws Exception{
		    // Retrieve the assignment from the database using the assignmentId
		    Assignment z = assignmentRepository.findById(id).orElse(null);
		    if (z != null)
				throw new Exception("Did not Delete!!");
		    assignmentRepository.delete(z);
	}

}
