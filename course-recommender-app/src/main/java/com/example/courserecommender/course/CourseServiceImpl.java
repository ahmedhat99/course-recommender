package com.example.courserecommender.course;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.courserecommender.author.Author;
import com.example.courserecommender.author.AuthorRepository;
import com.example.courserecommender.exception.ResourceNotFoundException;
import com.example.courserecommender.mapper.CourseMapper;
import com.example.recommendercore.CourseRecommender;
import com.example.recommendercore.RecommendedCourse;


@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRecommender courseRecommender;
    private final CourseRepository courseRepository;
    private final AuthorRepository authorRepository;
    private CourseMapper courseMapper;

    public CourseServiceImpl(
            @Qualifier("MockServerRecommender") CourseRecommender courseRecommender,
            CourseRepository courseRepository,
            CourseMapper courseMapper, AuthorRepository authorRepository) {
        this.courseRecommender = courseRecommender;
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Course> getRecommendedCourses() {
        // List<Course> allCourses = courseRepository.findAll();

        // List<RecommendedCourse> recommendedInput = courseMapper.toRecommendedCourseList(allCourses);
        List<RecommendedCourse> recommendedOutput = courseRecommender.recommendedCourses(Collections.emptyList());

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

    @Override
    public void addAuthorsToCourse(int courseId, List<Integer> authorIds) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with ID " + courseId + " not found."));

        List<Author> authors = authorRepository.findAllById(authorIds);

        List<Integer> foundIds = authors.stream()
                .map(Author::getId)
                .toList();

        List<Integer> missingIds = authorIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (authors.isEmpty()) {
            throw new ResourceNotFoundException("No authors found for provided IDs");
        }
        if (!missingIds.isEmpty()) {
            throw new ResourceNotFoundException("Authors not found for IDs: " + missingIds);
        }

        course.getAuthors().addAll(authors);
        courseRepository.save(course);
    }

    @Override
    public void removeAuthorsFromCourse(int courseId, List<Integer> authorIds) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with ID " + courseId + " not found."));

        List<Author> authorsToRemove = authorRepository.findAllById(authorIds);

        List<Integer> foundIds = authorsToRemove.stream()
                .map(Author::getId)
                .toList();

        List<Integer> missingIds = authorIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (authorsToRemove.isEmpty()) {
            throw new ResourceNotFoundException("No authors found for provided IDs");
        }
        if (!missingIds.isEmpty()) {
            throw new ResourceNotFoundException("Authors not found for IDs: " + missingIds);
        }

        course.getAuthors().removeAll(authorsToRemove);
        courseRepository.save(course);
    }

}