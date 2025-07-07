package com.example.courserecommender.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.courserecommender.course.Course;
import com.example.courserecommender.course.CourseService;
import com.example.courserecommender.dto.AuthorIdsDto;
import com.example.courserecommender.dto.CourseDto;
import com.example.courserecommender.mapper.CourseMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get course by ID", description = "Retrieves detailed information about a course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the course"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Course> viewCourse(
            @Parameter(description = "ID of the course to retrieve", example = "1") @PathVariable int id) {
        Course course = courseService.viewCourse(id);
        return ResponseEntity.ok(course);
    }

    @Operation(summary = "Add a new course", description = "Creates a new course with name, description, and credit & returns a location header with the URI of the created course.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Course successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid course data provided")
    })
    @PostMapping
    public ResponseEntity<Void> addCourse(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New Course data", required = true) @Valid @RequestBody CourseDto dto,
            UriComponentsBuilder uriBuilder) {

        Course course = courseMapper.toCourse(dto);
        Course savedCourse = courseService.addCourse(course);

        URI location = uriBuilder.path("/courses/{id}")
                .buildAndExpand(savedCourse.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update an existing course", description = "Updates the name, description, and credit of the course with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCourse(@PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated course data", required = true) @Valid @RequestBody CourseDto dto) {
        Course updatedCourse = courseMapper.toCourse(id, dto);
        courseService.updateCourse(updatedCourse);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a course", description = "Deletes the course with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(
            @Parameter(description = "ID of the course to delete") @PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Discover recommended courses", description = "Retrieves a list of recommended courses based on the recommendation logic.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of recommended courses")
    })
    @GetMapping("/discover")
    public ResponseEntity<List<Course>> discoverCourses() {
        List<Course> recommended = courseService.getRecommendedCourses();
        return ResponseEntity.ok(recommended);
    }

    @Operation(summary = "Get paginated list of courses", description = "Retrieves a paginated list of courses. According to specified page number and page size in query parameters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated list of courses")
    })
    @GetMapping
    public ResponseEntity<Page<Course>> getCourses(
            @Parameter(description = "Page number starting from 0") @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Number of courses per page") @RequestParam(defaultValue = "10") @Min(1) int size) {
        Page<Course> coursePage = courseService.findCoursesPaginated(page, size);
        return ResponseEntity.ok(coursePage);
    }

    @Operation(summary = "Add authors to a course", description = "Adds one or more existing authors to the course with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Authors successfully added to the course"),
            @ApiResponse(responseCode = "404", description = "Course or one or more authors not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/{id}/authors")
    public ResponseEntity<Void> addAuthorsToCourse(
            @Parameter(description = "ID of the course to which authors will be added") @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "list of IDs of the authors which will be added to the course", required = true) @Valid @RequestBody AuthorIdsDto dto) {

        courseService.addAuthorsToCourse(id, dto.getAuthorIds());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove authors from a course", description = "Removes one or more existing authors from the course with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Authors successfully removed from the course"),
            @ApiResponse(responseCode = "404", description = "Course or one or more authors not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/{id}/authors/remove")
    public ResponseEntity<Void> removeAuthorsFromCourse(
            @Parameter(description = "ID of the course from which authors will be removed") @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "List of IDs of the authors to be removed from the course", required = true) @Valid @RequestBody AuthorIdsDto dto) {

        courseService.removeAuthorsFromCourse(id, dto.getAuthorIds());
        return ResponseEntity.noContent().build();
    }

}
