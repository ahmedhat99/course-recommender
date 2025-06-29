package com.example.courserecommender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.courserecommender.author.AuthorRepository;
import com.example.courserecommender.author.AuthorService;
import com.example.courserecommender.controller.CourseController;
import com.example.courserecommender.course.Course;
import com.example.courserecommender.course.CourseRepository;
import com.example.courserecommender.course.CourseService;
import com.example.courserecommender.dto.CourseDto;
import com.example.courserecommender.exception.ResourceNotFoundException;
import com.example.courserecommender.mapper.CourseMapper;
import com.example.courserecommender.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private CourseMapper courseMapper;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testViewCourse_whenExists_returnsOk() throws Exception {
        Course course = new Course("Test Course", "Course Description", 2);

        when(courseService.viewCourse(1)).thenReturn(course);

        mockMvc.perform(get("/courses/1")
                .header("x-validation-report", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Course"))
                .andExpect(jsonPath("$.description").value("Course Description"))
                .andExpect(jsonPath("$.credit").value(2));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testViewCourse_whenNotExists_returnsNotFound() throws Exception {
        when(courseService.viewCourse(anyInt()))
                .thenThrow(new ResourceNotFoundException("Course with ID 1 not found."));

        mockMvc.perform(get("/courses/1").header("x-validation-report", "true"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAddCourse_whenValid_returnsCreated() throws Exception {
        CourseDto dto = new CourseDto("Test Course", "Course Description", 3);
        Course course = new Course();
        Course savedCourse = new Course();
        savedCourse.setId(1);

        when(courseMapper.toCourse(any(CourseDto.class))).thenReturn(course);
        when(courseService.addCourse(course)).thenReturn(savedCourse);

        mockMvc.perform(post("/courses").header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/courses/1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAddCourse_whenInvalid_returnsBadRequest() throws Exception {
        CourseDto dto = new CourseDto("", "short", 100);
        mockMvc.perform(post("/courses").header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateCourse_whenValid_returnsNoContent() throws Exception {
        CourseDto dto = new CourseDto("Updated Course", "Updated Description", 4);
        Course updatedCourse = new Course();

        when(courseMapper.toCourse(anyInt(),
                any(CourseDto.class))).thenReturn(updatedCourse);
        when(courseService.updateCourse(updatedCourse)).thenReturn(updatedCourse);

        mockMvc.perform(put("/courses/1").header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateCourse_whenInvalid_returnsBadRequest() throws Exception {
        CourseDto dto = new CourseDto("", "short", 100);
        mockMvc.perform(put("/courses/1").header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateCourse_whenNotExists_returnsNotFound() throws Exception {
        CourseDto dto = new CourseDto("Updated Name", "Updated Description", 3);
        Course updatedCourse = new Course();

        when(courseMapper.toCourse(anyInt(),
                any(CourseDto.class))).thenReturn(updatedCourse);
        when(courseService.updateCourse(updatedCourse))
                .thenThrow(new ResourceNotFoundException("Course with ID 1 not found."));

        mockMvc.perform(put("/courses/1").header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteCourse_whenExists_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/courses/1").header("x-validation-report", "true"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteCourse_whenNotExists_returnsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Course with ID 1 not found."))
                .when(courseService).deleteCourse(anyInt());

        mockMvc.perform(delete("/courses/1").header("x-validation-report", "true"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDiscoverCourses_returnsRecommendedCourses() throws Exception {
        List<Course> courses = List.of(
                new Course(1, "Test Course 1", "Description 1", 3),
                new Course(2, "Test Course 2", "Description 2", 2));

        when(courseService.getRecommendedCourses()).thenReturn(courses);

        mockMvc.perform(get("/courses/discover").header("x-validation-report", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Course 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Test Course 2"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetCourses_returnsPagedCourses() throws Exception {
        List<Course> courseList = List.of(
                new Course(1, "Test Course 1", "Description 1", 3),
                new Course(2, "Test Course 2", "Description 2", 2));

        Page<Course> coursePage = new PageImpl<>(courseList, PageRequest.of(0, 10),
                2);

        when(courseService.findCoursesPaginated(anyInt(),
                anyInt())).thenReturn(coursePage);

        mockMvc.perform(get("/courses").header("x-validation-report", "true")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Test Course 1"))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].name").value("Test Course 2"))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetCourses_whenInvalid_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/courses").header("x-validation-report", "true")
                .param("page", "a")
                .param("size", "b"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/courses").header("x-validation-report", "true")
                .param("page", "-1")
                .param("size", "0"))
                .andExpect(status().isBadRequest());
    }

}