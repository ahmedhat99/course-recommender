package com.example.courserecommender.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.recommendercore.Course;
import com.example.recommendercore.CourseRecommender;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRecommender courseRecommender;
    private final CourseRepository courseRepository;

    public CourseServiceImpl(
            @Qualifier("mainRecommender") CourseRecommender courseRecommender,
            CourseRepository courseRepository) {
        this.courseRecommender = courseRecommender;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getRecommendedCourses() {
        List<Course> allCourses = courseRepository.findAll();
        return courseRecommender.recommendedCourses(allCourses);
    }

    @Override
    public void addCourse(Course course) {
        courseRepository.addCourse(course);
    }

    @Override
    public void updateCourse(Course course) {
        courseRepository.updateCourse(course);
    }

    @Override
    public Course viewCourse(int id) {
        return courseRepository.viewCourse(id);
    }

    @Override
    public void deleteCourse(int id) {
        courseRepository.deleteCourse(id);
    }
}