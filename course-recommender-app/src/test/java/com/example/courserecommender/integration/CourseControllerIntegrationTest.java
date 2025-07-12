package com.example.courserecommender.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.courserecommender.course.Course;
import com.example.courserecommender.course.CourseRepository;
import com.example.courserecommender.dto.CourseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CourseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAddCourse_whenValid_returnsCreated() throws Exception {
        CourseDto dto = new CourseDto("Test Course", "Course Description", 3);

        mockMvc.perform(post("/courses")
                .header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        List<Course> allCourses = courseRepository.findAll();
        assertThat(allCourses).isNotEmpty();
        Course saved = allCourses.get(0);
        assertThat(saved.getName()).isEqualTo("Test Course");
        assertThat(saved.getDescription()).isEqualTo("Course Description");
        assertThat(saved.getCredit()).isEqualTo(3);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testViewCourse_whenExists_returnsOk() throws Exception {
        Course saved = courseRepository.save(new Course("Test Course", "Course Description", 2));

        String response = mockMvc.perform(get("/courses/" + saved.getId())
                .header("x-validation-report", "true"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Course returned = objectMapper.readValue(response, Course.class);

        assertThat(returned.getId()).isEqualTo(saved.getId());
        assertThat(returned.getName()).isEqualTo("Test Course");
        assertThat(returned.getDescription()).isEqualTo("Course Description");
        assertThat(returned.getCredit()).isEqualTo(2);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testViewCourse_whenNotExists_returnsNotFound() throws Exception {
        mockMvc.perform(get("/courses/9999")
                .header("x-validation-report", "true"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateCourse_whenValid_returnsNoContent() throws Exception {
        Course saved = courseRepository.save(new Course("Test Course", "Course Description", 1));

        CourseDto updatedDto = new CourseDto("Test Course", "Course Description", 4);

        mockMvc.perform(put("/courses/" + saved.getId())
                .header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isNoContent());

        Course updated = courseRepository.findById(saved.getId()).orElseThrow();
        assertThat(updated.getName()).isEqualTo("Test Course");
        assertThat(updated.getDescription()).isEqualTo("Course Description");
        assertThat(updated.getCredit()).isEqualTo(4);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteCourse_whenExists_returnsNoContent() throws Exception {
        Course saved = courseRepository.save(new Course("Test Course", "Course Description", 2));

        mockMvc.perform(delete("/courses/" + saved.getId())
                .header("x-validation-report", "true"))
                .andExpect(status().isNoContent());

        assertThat(courseRepository.existsById(saved.getId())).isFalse();
    }

    // @Test
    // @WithMockUser(username = "admin", roles = "ADMIN")
    // void testDiscoverCourses_returnsRecommendedCourses() throws Exception {
    //     courseRepository.save(new Course("Test Course 1", "Course Description", 4));
    //     courseRepository.save(new Course("Test Course 2", "Course Description", 2));

    //     String response = mockMvc.perform(get("/courses/discover")
    //             .header("x-validation-report", "true"))
    //             .andExpect(status().isOk())
    //             .andReturn().getResponse().getContentAsString();

    //     List<Course> courses = objectMapper.readValue(response, new TypeReference<List<Course>>() {});

    //     assertThat(courses).hasSize(1);
    //     assertThat(courses.get(0).getName()).isEqualTo("Test Course 1");
    // }
}
