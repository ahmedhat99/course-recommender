package com.example.courserecommender.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.courserecommender.author.Author;
import com.example.courserecommender.author.AuthorService;
import com.example.courserecommender.dto.AuthorDto;
import com.example.courserecommender.mapper.AuthorMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Add a new author", description = "Creates a new author and returns a location header with the URI of the created author.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid author data provided")
    })
    @PostMapping
    public ResponseEntity<Void> addAuthor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New Author data", required = true)
            @Valid @RequestBody AuthorDto dto,
            UriComponentsBuilder uriBuilder) {

        Author author = authorMapper.toAuthor(dto);
        Author savedAuthor = authorService.addAuthor(author);

        URI location = uriBuilder.path("/authors/{id}")
                .buildAndExpand(savedAuthor.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update an existing author", description = "Updates the name, email, and birthdate of the author with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthor(
            @Parameter(description = "ID of the author to update") @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated author data", required = true)
            @Valid @RequestBody AuthorDto dto) {
        Author authorToUpdate = authorMapper.toAuthor(id, dto);
        authorService.updateAuthor(authorToUpdate);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get author by ID", description = "Retrieves detailed information about an author.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the author"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Author> viewAuthor(
            @Parameter(description = "ID of the author to retrieve") @PathVariable int id) {
        Author author = authorService.viewAuthor(id);
        return ResponseEntity.ok(author);
    }

    @Operation(summary = "Delete an author", description = "Deletes the author with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(
            @Parameter(description = "ID of the author to delete") @PathVariable int id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Find author by email", description = "Searches for an author using their email address.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the author"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @GetMapping("/search")
    public ResponseEntity<Author> getAuthorByEmail(
            @Parameter(description = "Email address of the author to search for") @RequestParam String email) {
        Author author = authorService.findByEmail(email);
        return ResponseEntity.ok(author);
    }
}
