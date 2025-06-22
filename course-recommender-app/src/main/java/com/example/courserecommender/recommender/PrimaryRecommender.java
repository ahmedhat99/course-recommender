package com.example.courserecommender.recommender;

import java.util.List;

import com.example.recommendercore.CourseRecommender;
import com.example.recommendercore.RecommendedCourse;

public class PrimaryRecommender implements CourseRecommender {
    public List<RecommendedCourse> recommendedCourses(List<RecommendedCourse> allCourses) {
        return List.of(new RecommendedCourse("primary1"), new RecommendedCourse("primary2"));
    }
}

