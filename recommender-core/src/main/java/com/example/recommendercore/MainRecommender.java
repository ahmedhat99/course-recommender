package com.example.recommendercore;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("mainRecommender")
public class MainRecommender implements CourseRecommender {
        public List<Course> recommendedCourses() {
        return List.of(new Course("main1"), new Course("main2"));
    }
}