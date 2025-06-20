package com.example.courserecommender;

import java.util.List;

import com.example.recommendercore.Course;
import com.example.recommendercore.CourseRecommender;

public class PrimaryRecommender implements CourseRecommender {
    public List<Course> recommendedCourses() {
        return List.of(new Course("primary1"), new Course("primary2"));
    }
}

