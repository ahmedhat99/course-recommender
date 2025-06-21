package com.example.courserecommender.recommender;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.recommendercore.Course;
import com.example.recommendercore.CourseRecommender;

@Component("secondaryRecommender")
public class SecondaryRecommender implements CourseRecommender {
    public List<Course> recommendedCourses(List<Course> allCourses) {
        return List.of(new Course("secondary1"), new Course("secondary2"));
    }
}
