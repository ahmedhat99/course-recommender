package com.example.courserecommender.course;

import java.util.List;

import org.springframework.data.domain.Page;

public interface CourseService {

    List<Course> getRecommendedCourses();

    Course addCourse(Course course);

    Course updateCourse(Course course);

    Course viewCourse(int id);

    void deleteCourse(int id);

    Page<Course> findCoursesPaginated(int page, int size);

    void addAuthorsToCourse(int courseId, List<Integer> authorIds); 
    
    void removeAuthorsFromCourse(int courseId, List<Integer> authorIds);
}