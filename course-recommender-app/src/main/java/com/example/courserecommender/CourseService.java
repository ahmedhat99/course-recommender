package com.example.courserecommender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private CourseRecommender courseRecommender;

    // @Autowired
    // private CourseRecommender secondaryRecommender;

    public CourseService(CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }

    // public CourseService(@Qualifier("secondaryRecommender") CourseRecommender
    // courseRecommender) {
    // this.courseRecommender = courseRecommender;
    // }

    // @Autowired
    // public void setCourseRecommender(CourseRecommender courseRecommender) {
    // this.courseRecommender = courseRecommender;
    // }

    public List<Course> getRecommendedCourses() {
        return courseRecommender.recommendedCourses();
        // return secondaryRecommender.recommendedCourses();
    }

}
