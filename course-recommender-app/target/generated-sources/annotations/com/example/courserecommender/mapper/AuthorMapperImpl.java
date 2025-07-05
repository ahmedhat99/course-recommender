package com.example.courserecommender.mapper;

import com.example.courserecommender.author.Author;
import com.example.courserecommender.dto.AuthorDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-06T00:06:26+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
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
            author.setName( dto.getName() );
            author.setEmail( dto.getEmail() );
            author.setBirthdate( dto.getBirthdate() );
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

        author.setName( dto.getName() );
        author.setEmail( dto.getEmail() );
        author.setBirthdate( dto.getBirthdate() );

        return author;
    }
}
