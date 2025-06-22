package com.example.recommendercore;

import java.util.List;


public interface CourseRecommender {
    List<RecommendedCourse> recommendedCourses(List<RecommendedCourse> allCourses);
}
