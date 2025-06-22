package com.example.recommendercore;

import java.util.List;

import org.springframework.stereotype.Component;


@Component("mainRecommender")
public class MainRecommender implements CourseRecommender {
        public List<RecommendedCourse> recommendedCourses(List<RecommendedCourse> allCourses) {
        return List.of(new RecommendedCourse("main1"), new RecommendedCourse("main2"));
    }
}