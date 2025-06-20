package com.example.courserecommender;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("secondaryRecommender")
public class SecondaryRecommender implements CourseRecommender {
    public List<Course> recommendedCourses() {
        return List.of(new Course("secondary1"), new Course("secondary2"));
    }
}
