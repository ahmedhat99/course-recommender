package com.example.courserecommender;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseServiceTest {

    private ApplicationContext context;
    private CourseService courseService;

    @BeforeAll
    void setUp() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        courseService = context.getBean(CourseService.class);
    }


    @Test
    void testRecommenderInjected() {
        List<Course> courses = courseService.getRecommendedCourses();
        assertEquals(2, courses.size());
        assertEquals("primary1", courses.get(0).getTitle());
        assertEquals("primary2", courses.get(1).getTitle());
    }

}
