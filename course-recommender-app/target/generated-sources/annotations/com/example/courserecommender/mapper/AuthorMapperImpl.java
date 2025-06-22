package com.example.courserecommender.mapper;

import com.example.courserecommender.author.Author;
import com.example.courserecommender.dto.AuthorDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-22T07:07:57+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public Author toAuthor(int id, AuthorDto dto) {
        if ( dto == null ) {
            return null;
        }

        Author author = new Author();

        if ( dto != null ) {
            author.setBirthdate( dto.getBirthdate() );
            author.setEmail( dto.getEmail() );
            author.setName( dto.getName() );
        }
        author.setId( id );

        return author;
    }

    @Override
    public Author toAuthor(AuthorDto dto) {
        if ( dto == null ) {
            return null;
        }

        Author author = new Author();

        author.setBirthdate( dto.getBirthdate() );
        author.setEmail( dto.getEmail() );
        author.setName( dto.getName() );

        return author;
    }
}
