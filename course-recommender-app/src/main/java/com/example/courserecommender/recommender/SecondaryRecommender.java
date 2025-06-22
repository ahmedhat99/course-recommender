package com.example.courserecommender.recommender;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.recommendercore.CourseRecommender;
import com.example.recommendercore.RecommendedCourse;

@Component("secondaryRecommender")
public class SecondaryRecommender implements CourseRecommender {
    public List<RecommendedCourse> recommendedCourses(List<RecommendedCourse> allCourses) {
        return List.of(new RecommendedCourse("secondary1"), new RecommendedCourse("secondary2"));
    }
}
