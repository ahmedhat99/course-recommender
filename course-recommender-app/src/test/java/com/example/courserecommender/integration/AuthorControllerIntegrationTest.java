package com.example.courserecommender.integration;


import com.example.courserecommender.author.Author;
import com.example.courserecommender.author.AuthorRepository;
import com.example.courserecommender.dto.AuthorDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAddAuthor_whenValid_returnsCreated() throws Exception {
        AuthorDto dto = new AuthorDto("Test", "test@example.com", LocalDate.of(1990, 1, 1));

        mockMvc.perform(post("/authors")
                .header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated());

        Author saved = authorRepository.findAll().get(0);
        assertThat(saved.getName()).isEqualTo("Test");
        assertThat(saved.getEmail()).isEqualTo("test@example.com");
        assertThat(saved.getBirthdate()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAddAuthor_whenInvalid_returnsBadRequest() throws Exception {
        AuthorDto dto = new AuthorDto("", "invalid-email", null);

        mockMvc.perform(post("/authors")
                .header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateAuthor_whenValid_returnsNoContent() throws Exception {
        Author author = authorRepository.save(new Author("Test", "test@example.com", LocalDate.of(1990, 1, 1)));
        AuthorDto dto = new AuthorDto("Updated", "updated@example.com", LocalDate.of(1991, 2, 2));

        mockMvc.perform(put("/authors/" + author.getId())
                .header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNoContent());

        Optional<Author> updated = authorRepository.findById(author.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getName()).isEqualTo("Updated");
        assertThat(updated.get().getEmail()).isEqualTo("updated@example.com");
        assertThat(updated.get().getBirthdate()).isEqualTo(LocalDate.of(1991, 2, 2));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateAuthor_whenInvalid_returnsBadRequest() throws Exception {
        AuthorDto dto = new AuthorDto("", "invalid-email", null);

        mockMvc.perform(put("/authors/1")
                .header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateAuthor_whenNotExists_returnsNotFound() throws Exception {
        AuthorDto dto = new AuthorDto("Test", "test@example.com", LocalDate.of(1990, 1, 1));

        mockMvc.perform(put("/authors/999")
                .header("x-validation-report", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testViewAuthor_whenExists_returnsOk() throws Exception {
        Author author = authorRepository.save(new Author("Test", "test@example.com", LocalDate.of(1990, 1, 1)));

        String response = mockMvc.perform(get("/authors/" + author.getId())
                .header("x-validation-report", "true"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        AuthorDto dto = objectMapper.readValue(response, AuthorDto.class);
        assertThat(dto.getName()).isEqualTo("Test");
        assertThat(dto.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testViewAuthor_whenNotExists_returnsNotFound() throws Exception {
        mockMvc.perform(get("/authors/999")
                .header("x-validation-report", "true"))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteAuthor_whenExists_returnsNoContent() throws Exception {
        Author author = authorRepository.save(new Author("Test", "test@example.com", LocalDate.of(1990, 1, 1)));

        mockMvc.perform(delete("/authors/" + author.getId())
                .header("x-validation-report", "true"))
            .andExpect(status().isNoContent());

        assertThat(authorRepository.existsById(author.getId())).isFalse();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteAuthor_whenNotExists_returnsNotFound() throws Exception {
        mockMvc.perform(delete("/authors/999")
                .header("x-validation-report", "true"))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetAuthorByEmail_whenExists_returnsOk() throws Exception {
        authorRepository.save(new Author("Test", "test@example.com", LocalDate.of(1990, 1, 1)));

        String response = mockMvc.perform(get("/authors/search")
                .header("x-validation-report", "true")
                .param("email", "test@example.com"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        AuthorDto dto = objectMapper.readValue(response, AuthorDto.class);
        assertThat(dto.getName()).isEqualTo("Test");
        assertThat(dto.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetAuthorByEmail_whenNotExists_returnsNotFound() throws Exception {
        mockMvc.perform(get("/authors/search")
                .header("x-validation-report", "true")
                .param("email", "notfound@example.com"))
            .andExpect(status().isNotFound());
    }
}
