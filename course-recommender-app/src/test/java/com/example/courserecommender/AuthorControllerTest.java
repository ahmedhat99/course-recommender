package com.example.courserecommender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.courserecommender.author.Author;
import com.example.courserecommender.author.AuthorRepository;
import com.example.courserecommender.author.AuthorService;
import com.example.courserecommender.controller.AuthorController;
import com.example.courserecommender.course.CourseRepository;
import com.example.courserecommender.course.CourseService;
import com.example.courserecommender.dto.AuthorDto;
import com.example.courserecommender.exception.ResourceNotFoundException;
import com.example.courserecommender.mapper.AuthorMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private AuthorMapper authorMapper;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    @Test
    void testAddAuthor_whenValid_returnsCreated() throws Exception {
        AuthorDto dto = new AuthorDto("Test", "Test@example.com", LocalDate.of(1990, 1, 1));
        Author author = new Author();
        Author savedAuthor = new Author();
        savedAuthor.setId(1);

        when(authorMapper.toAuthor(any(AuthorDto.class))).thenReturn(author);
        when(authorService.addAuthor(author)).thenReturn(savedAuthor);

        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/authors/1"));
    }

    @Test
    void testAddAuthor_whenInvalid_returnsBadRequest() throws Exception {
        AuthorDto dto = new AuthorDto("", "invalid-email", null);

        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateAuthor_whenValid_returnsNoContent() throws Exception {
        AuthorDto dto = new AuthorDto("Test", "test@example.com", LocalDate.of(1990, 1, 1));
        Author updatedAuthor = new Author();

        when(authorMapper.toAuthor(anyInt(), any(AuthorDto.class))).thenReturn(updatedAuthor);
        when(authorService.updateAuthor(updatedAuthor)).thenReturn(updatedAuthor);

        mockMvc.perform(put("/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateAuthor_whenInvalid_returnsBadRequest() throws Exception {
        AuthorDto dto = new AuthorDto("", "invalid-email", LocalDate.of(1990, 1, 1));

        mockMvc.perform(put("/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateAuthor_whenNotExists_returnsNotFound() throws Exception {
        AuthorDto dto = new AuthorDto("Test", "test@example.com", LocalDate.of(1990, 1, 1));
        Author authorToUpdate = new Author();

        when(authorMapper.toAuthor(anyInt(), any(AuthorDto.class))).thenReturn(authorToUpdate);
        when(authorService.updateAuthor(authorToUpdate))
                .thenThrow(new ResourceNotFoundException("Author with ID 1 not found."));

        mockMvc.perform(put("/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testViewAuthor_whenExists_returnsOk() throws Exception {
        Author author = new Author("Test", "test@example.com", LocalDate.of(1990, 1, 1));

        when(authorService.viewAuthor(1)).thenReturn(author);

        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testViewAuthor_whenNotExists_returnsNotFound() throws Exception {
        when(authorService.viewAuthor(anyInt()))
                .thenThrow(new ResourceNotFoundException("Author with ID 1 not found."));

        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAuthor_whenExists_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/authors/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteAuthor_whenNotExists_returnsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Author with ID 1 not found."))
                .when(authorService).deleteAuthor(anyInt());

        mockMvc.perform(delete("/authors/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAuthorByEmail_whenExists_returnsOk() throws Exception {
        Author author = new Author("Test", "test@example.com", LocalDate.of(1990, 1, 1));

        when(authorService.findByEmail("test@example.com")).thenReturn(author);

        mockMvc.perform(get("/authors/search")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetAuthorByEmail_whenNotExists_returnsNotFound() throws Exception {
        when(authorService.findByEmail(anyString()))
                .thenThrow(new ResourceNotFoundException("Author with email test@example.com not found."));

        mockMvc.perform(get("/authors/search")
                .param("email", "test@example.com"))
                .andExpect(status().isNotFound());
    }
}