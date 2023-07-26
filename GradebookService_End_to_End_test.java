package com.cst438;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class GradebookService_End_to_End_test
{
	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";
	public static final String URL = "http://localhost:3000";
	public static final String TEST_USER_EMAIL = "test@csumb.edu";
	public static final String TEST_INSTRUCTOR_EMAIL = "dwisneski@csumb.edu";
	public static final int SLEEP_DURATION = 1000; // 1 second.
	public static final int TEST_ASSIGNMENT_ID = 6;
	public static final String TEST_ASSIGNMENT_DATE = "2022-12-25";
	public static final String TEST_COURSE_ID = "40443";
	public static final String TEST_COURSE_TITLE = "Test Course";
	public static final String TEST_ASSIGNMENT_NAME = "Test New Assignment";

	@Autowired
	EnrollmentRepository enrollmentRepository;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	AssignmentGradeRepository assignnmentGradeRepository;
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Test
	public void testNewAssignment() throws Exception
	{
		ChromeOptions opt = new ChromeOptions();
		System.setProperty("webdriver.chrome.driver",CHROME_DRIVER_FILE_LOCATION);
		opt.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(opt);
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		driver.get(URL);
		Thread.sleep(SLEEP_DURATION);
		
		try {
			WebElement we = driver.findElement(By.id("b1"));
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			we = driver.findElement(By.name("assignmentName"));
			we.sendKeys(TEST_ASSIGNMENT_NAME);
			we = driver.findElement(By.name("dueDate"));
			we.sendKeys(TEST_ASSIGNMENT_DATE);
			we = driver.findElement(By.name("courseId"));
			we.sendKeys(TEST_COURSE_ID);
			
			we = driver.findElement(By.id("handleAdd"));
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			Assignment a = assignmentRepository.findByName(TEST_ASSIGNMENT_NAME);
			assertNotNull(a);
			if (a != null)
				assignmentRepository.delete(a);
		}catch (Exception ex) {
			throw ex;
		} finally {
			Assignment a = assignmentRepository.findByName(TEST_ASSIGNMENT_NAME);
			
			if (a != null)
				assignmentRepository.delete(a);
			driver.quit();
		}
	}
}
