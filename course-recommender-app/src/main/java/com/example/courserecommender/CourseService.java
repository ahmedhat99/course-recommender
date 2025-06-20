package com.example.courserecommender;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.recommendercore.Course;
import com.example.recommendercore.CourseRecommender;

@Service
public class CourseService {

    private CourseRecommender courseRecommender;

    public CourseService(@Qualifier("mainRecommender") CourseRecommender
    courseRecommender) {
    this.courseRecommender = courseRecommender;
    }  

    public List<Course> getRecommendedCourses() {
        return courseRecommender.recommendedCourses();
    }

}
