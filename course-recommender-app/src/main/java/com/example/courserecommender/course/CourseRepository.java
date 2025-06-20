package com.example.courserecommender.course;

import com.example.recommendercore.Course;

public interface CourseRepository {

    void addCourse(Course course);

    void updateCourse(Course course);

    Course viewCourse(int id);

    void deleteCourse(int id);
}