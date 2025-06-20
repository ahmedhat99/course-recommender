package com.example.courserecommender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.courserecommender.config.AppConfig;
import com.example.courserecommender.course.CourseService;
import com.example.recommendercore.Course;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseServiceTest {

    private ApplicationContext context;
    private CourseService courseService;

    @BeforeAll
    void setUp() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        courseService = context.getBean(CourseService.class);
    }

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbc = context.getBean(JdbcTemplate.class);
        jdbc.update("DELETE FROM Course");
    }

    @Test
    void testRecommenderInjected() {
        List<Course> courses = courseService.getRecommendedCourses();
        assertEquals(2, courses.size());
        assertEquals("override1", courses.get(0).getName());
        assertEquals("override2", courses.get(1).getName());
    }


    @Test
void testAddAndViewCourse() {
    Course newCourse = new Course(1, "Test Course", "A course for testing", 4, 1);
    courseService.addCourse(newCourse);

    Course course = courseService.viewCourse(1);
    assertEquals("Test Course", course.getName()); 
}

@Test
void testUpdateCourse() {
    Course course = new Course(1, "Test Course", "A course for testing", 4, 1);
    courseService.addCourse(course);

    Course fetched = courseService.viewCourse(1);
    assertEquals("Test Course", fetched.getName());

    Course updated = new Course(1, "Updated Course", "Updated description", 5, 1);
    courseService.updateCourse(updated);

    Course updatedCourse = courseService.viewCourse(1);
    assertEquals("Updated Course", updatedCourse.getName());
    assertEquals("Updated description", updatedCourse.getDescription());
    assertEquals(5, updatedCourse.getCredit());
}

@Test
void testDeleteCourse() {
    Course course = new Course(1, "Test Course", "A course for testing", 4, 1);
    courseService.addCourse(course);

    Course fetched = courseService.viewCourse(1);
    assertEquals("Test Course", fetched.getName());

    courseService.deleteCourse(1);

    Course deleted = courseService.viewCourse(1);
    assertNull(deleted);
}

}
