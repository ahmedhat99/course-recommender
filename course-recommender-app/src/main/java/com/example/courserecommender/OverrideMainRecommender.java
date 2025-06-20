package com.example.courserecommender;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.recommendercore.Course;
import com.example.recommendercore.CourseRecommender;

@Component("mainRecommender")
public class OverrideMainRecommender implements CourseRecommender {
    public List<Course> recommendedCourses() {
        return List.of(new Course("override1"), new Course("override2"));
    }
}