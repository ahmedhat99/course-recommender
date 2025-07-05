package com.example.courserecommender.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.courserecommender.author.Author;
import com.example.courserecommender.author.AuthorRepository;
import com.example.courserecommender.author.AuthorServiceImpl;
import com.example.courserecommender.exception.ResourceNotFoundException;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAuthor_savesAndReturnsAuthor() {
        Author inputAuthor = new Author();
        Author savedAuthor = new Author();

        when(authorRepository.save(inputAuthor)).thenReturn(savedAuthor);

        Author result = authorService.addAuthor(inputAuthor);

        assertEquals(savedAuthor, result);
        verify(authorRepository).save(inputAuthor);
    }

    @Test
    void testUpdateAuthor_whenExists_savesAndReturnsAuthor() {
        Author inputAuthor = new Author();
        Author updatedAuthor = new Author();

        when(authorRepository.existsById(anyInt())).thenReturn(true);
        when(authorRepository.save(inputAuthor)).thenReturn(updatedAuthor);

        Author result = authorService.updateAuthor(inputAuthor);

        assertEquals(updatedAuthor, result);
        verify(authorRepository).existsById(anyInt());
        verify(authorRepository).save(inputAuthor);
    }

    @Test
    void testUpdateAuthor_whenNotExists_throwsException() {
        Author author = new Author();
        author.setId(1);

        when(authorRepository.existsById(1)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> authorService.updateAuthor(author));

        assertEquals("Cannot update. Author with ID 1 not found.", exception.getMessage());
        verify(authorRepository).existsById(1);
        verify(authorRepository, never()).save(any());
    }

    @Test
    void testViewAuthor_whenExists_returnsAuthor() {
        Author author = new Author();
        when(authorRepository.findById(anyInt())).thenReturn(Optional.of(author));

        Author result = authorService.viewAuthor(1);

        assertEquals(author, result);
        verify(authorRepository).findById(1);
    }

    @Test
    void testViewAuthor_whenNotExists_throwsException() {
        when(authorRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> authorService.viewAuthor(1));

        assertEquals("Author with ID 1 not found.", exception.getMessage());
        verify(authorRepository).findById(1);
    }

    @Test
    void testDeleteAuthor_whenExists_deletesAuthor() {
        when(authorRepository.existsById(anyInt())).thenReturn(true);

        authorService.deleteAuthor(1);

        verify(authorRepository).existsById(1);
        verify(authorRepository).deleteById(1);
    }

    @Test
    void testDeleteAuthor_whenNotExists_throwsException() {
        when(authorRepository.existsById(anyInt())).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> authorService.deleteAuthor(1));

        assertEquals("Cannot delete. Author with ID 1 not found.", exception.getMessage());
        verify(authorRepository).existsById(1);
        verify(authorRepository, never()).deleteById(any());
    }

    @Test
    void testFindByEmail_whenExists_returnsAuthor() {
        Author author = new Author();
        when(authorRepository.findByEmail(anyString())).thenReturn(Optional.of(author));

        Author result = authorService.findByEmail("test@example.com");

        assertEquals(author, result);
        verify(authorRepository).findByEmail("test@example.com");
    }

    @Test
    void testFindByEmail_whenNotExists_throwsException() {
        when(authorRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> authorService.findByEmail("test@example.com"));

        assertEquals("Author with email 'test@example.com' not found.", exception.getMessage());
        verify(authorRepository).findByEmail("test@example.com");
    }
}