package com.example.courserecommender;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component("primaryRecommender")
public class PrimaryRecommender implements CourseRecommender {
    public List<Course> recommendedCourses() {
        return List.of(new Course("primary1"), new Course("primary2"));
    }
}

