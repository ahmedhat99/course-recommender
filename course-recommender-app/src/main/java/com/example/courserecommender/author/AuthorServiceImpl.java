package com.example.courserecommender.author;

import org.springframework.stereotype.Service;

import com.example.courserecommender.exception.ResourceNotFoundException;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        if (!authorRepository.existsById(author.getId())) {
            throw new ResourceNotFoundException("Cannot update. Author with ID " + author.getId() + " not found.");
        }
        return authorRepository.save(author);
    }

    @Override
    public Author viewAuthor(int id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with ID " + id + " not found."));
    }

    @Override
    public void deleteAuthor(int id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Author with ID " + id + " not found.");
        }
        authorRepository.deleteById(id);
    }

    @Override
    public Author findByEmail(String email) {
        return authorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Author with email '" + email + "' not found."));
    }

}
