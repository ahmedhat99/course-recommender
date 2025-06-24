package com.example.courserecommender.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.courserecommender.course.Course;
import com.example.courserecommender.course.CourseService;
import com.example.courserecommender.dto.CourseDto;
import com.example.courserecommender.mapper.CourseMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> viewCourse(@PathVariable int id) {
        Course course = courseService.viewCourse(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<Void> addCourse(@Valid @RequestBody CourseDto dto, UriComponentsBuilder uriBuilder) {

        Course course = courseMapper.toCourse(dto);
        Course savedCourse = courseService.addCourse(course);

        URI location = uriBuilder.path("/courses/{id}")
                .buildAndExpand(savedCourse.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCourse(@PathVariable int id, @Valid @RequestBody CourseDto dto) {
        Course updatedCourse = courseMapper.toCourse(id, dto);
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

    @GetMapping
    public ResponseEntity<Page<Course>> getCourses(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        Page<Course> coursePage = courseService.findCoursesPaginated(page, size);
        return ResponseEntity.ok(coursePage);
    }

}
