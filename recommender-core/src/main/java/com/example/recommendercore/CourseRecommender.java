package com.example.recommendercore;

import java.util.List;

public interface CourseRecommender {
    List<Course> recommendedCourses(List<Course> allCourses);
}
