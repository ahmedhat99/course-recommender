package com.example.courserecommender.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.courserecommender.course.CourseService;
import com.example.courserecommender.dto.CourseDto;
import com.example.recommendercore.Course;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> viewCourse(@PathVariable int id) {
        Course course = courseService.viewCourse(id);

        return (course != null)
                ? ResponseEntity.ok(course)
                : ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<Void> addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCourse(@PathVariable int id, @RequestBody CourseDto dto) {
        Course updatedCourse = new Course(id, dto.getName(), dto.getDescription(), dto.getCredit(), dto.getAuthorId());
        courseService.updateCourse(updatedCourse);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/discover")
    public ResponseEntity<List<Course>> discoverCourses() {
        List<Course> recommended = courseService.getRecommendedCourses();
        return ResponseEntity.ok(recommended);
    }

}
