package com.example.courserecommender.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.courserecommender.exception.ResourceNotFoundException;
import com.example.courserecommender.mapper.CourseMapper;
import com.example.recommendercore.CourseRecommender;
import com.example.recommendercore.RecommendedCourse;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRecommender courseRecommender;
    private final CourseRepository courseRepository;
    private CourseMapper courseMapper;

    public CourseServiceImpl(
            @Qualifier("mainRecommender") CourseRecommender courseRecommender,
            CourseRepository courseRepository,
            CourseMapper courseMapper) {
        this.courseRecommender = courseRecommender;
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Course> getRecommendedCourses() {
        List<Course> allCourses = courseRepository.findAll();

        List<RecommendedCourse> recommendedInput = courseMapper.toRecommendedCourseList(allCourses);
        List<RecommendedCourse> recommendedOutput = courseRecommender.recommendedCourses(recommendedInput);

        return courseMapper.toCourseList(recommendedOutput);
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course) {
        if (!courseRepository.existsById(course.getId())) {
            throw new ResourceNotFoundException("Cannot update. Course with ID " + course.getId() + " not found.");
        }
        return courseRepository.save(course);
    }

    @Override
    public Course viewCourse(int id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course with ID " + id + " not found."));
    }

    @Override
    public void deleteCourse(int id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Course with ID " + id + " not found.");
        }
        courseRepository.deleteById(id);
    }

    public Page<Course> findCoursesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(pageable);
    }
}