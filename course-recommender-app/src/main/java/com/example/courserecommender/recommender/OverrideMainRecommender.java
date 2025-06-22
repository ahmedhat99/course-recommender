package com.example.courserecommender.recommender;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.recommendercore.CourseRecommender;
import com.example.recommendercore.RecommendedCourse;

@Component("mainRecommender")
public class OverrideMainRecommender implements CourseRecommender {

    @Override
    public List<RecommendedCourse> recommendedCourses(List<RecommendedCourse> allCourses) {
        return allCourses.stream()
                .filter(course -> course.getCredit() > 3)
                .collect(Collectors.toList());
    }
}