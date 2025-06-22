package com.example.courserecommender.author;

public interface AuthorService {

    public Author addAuthor(Author author);

    public Author updateAuthor(Author author);

    public Author viewAuthor(int id);

    public void deleteAuthor(int id);

    Author findByEmail(String email);

}
