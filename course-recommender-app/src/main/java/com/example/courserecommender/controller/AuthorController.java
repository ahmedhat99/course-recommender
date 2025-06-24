package com.example.courserecommender.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.courserecommender.author.Author;
import com.example.courserecommender.author.AuthorService;
import com.example.courserecommender.dto.AuthorDto;
import com.example.courserecommender.mapper.AuthorMapper;

import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    public ResponseEntity<Void> addAuthor(@Valid @RequestBody AuthorDto dto, UriComponentsBuilder uriBuilder) {

        Author author = authorMapper.toAuthor(dto);
        Author savedAuthor = authorService.addAuthor(author);

        URI location = uriBuilder.path("/authors/{id}")
                .buildAndExpand(savedAuthor.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthor(@PathVariable int id,@Valid @RequestBody AuthorDto dto) {
        Author authorToUpdate = authorMapper.toAuthor(id, dto);
        authorService.updateAuthor(authorToUpdate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> viewAuthor(@PathVariable int id) {
        Author author = authorService.viewAuthor(id);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Author> getAuthorByEmail(@RequestParam String email) {
        Author author = authorService.findByEmail(email);
        return ResponseEntity.ok(author);
    }
}