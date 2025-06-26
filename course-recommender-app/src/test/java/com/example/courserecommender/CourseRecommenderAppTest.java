// package com.example.courserecommender;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;

// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.jdbc.core.JdbcTemplate;

// import com.example.courserecommender.course.Course;
// import com.example.courserecommender.course.CourseService;
// import com.example.courserecommender.exception.ResourceNotFoundException;

// @SpringBootTest
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// public class CourseRecommenderAppTest {

//     @Autowired
//     private CourseService courseService;

//     @Autowired
//     private JdbcTemplate jdbcTemplate;

//     @BeforeEach
//     void clearBefore() {
//         jdbcTemplate.update("DELETE FROM Course");
//     }

//     @Test
//     void testRecommender() {
//         Course matchingCourse = new Course("Matching", "A course for testing", 4);
//         courseService.addCourse(matchingCourse);
//         Course nonMatchingCourse = new Course("Non-Matching", "A course for testing", 2);
//         courseService.addCourse(nonMatchingCourse);
//         List<Course> courses = courseService.getRecommendedCourses();
//         assertEquals(1, courses.size());
//         assertEquals("Matching", courses.get(0).getName());
//     }

//     @Test
//     void testAddAndViewCourse() {
//         Course newCourse = new Course("Test Course", "A course for testing", 4);
//         courseService.addCourse(newCourse);

//         Course course = courseService.viewCourse(1);
//         assertEquals("Test Course", course.getName());
//     }

//     @Test
//     void testUpdateCourse() {
//         Course course = new Course("Test Course", "A course for testing", 4);
//         Course savedCourse = courseService.addCourse(course);
//         int id = savedCourse.getId();

//         Course fetched = courseService.viewCourse(id);
//         assertEquals("Test Course", fetched.getName());

//         Course updated = new Course(id, "Updated Course", "Updated description", 5);
//         courseService.updateCourse(updated);

//         Course updatedCourse = courseService.viewCourse(id);
//         assertEquals("Updated Course", updatedCourse.getName());
//         assertEquals("Updated description", updatedCourse.getDescription());
//         assertEquals(5, updatedCourse.getCredit());
//     }

//     @Test
//     void testDeleteCourse() {
//         Course course = new Course("Test Course", "A course for testing", 4);
//         Course savedCourse = courseService.addCourse(course);
//         int id = savedCourse.getId();

//         Course fetched = courseService.viewCourse(id);
//         assertEquals("Test Course", fetched.getName());

//         courseService.deleteCourse(id);

//         assertThrows(ResourceNotFoundException.class, () -> courseService.viewCourse(id));
//     }

// }
