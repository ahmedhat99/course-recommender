package com.example.courserecommender.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.courserecommender.author.Author;
import com.example.courserecommender.dto.AuthorDto;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(target = "id", source = "id")

    Author toAuthor(int id, AuthorDto dto);

    Author toAuthor(AuthorDto dto);
}
