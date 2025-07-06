package com.example.courserecommender.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public class AuthorIdsDto {

    @NotEmpty(message = "Author IDs must not be empty")
    private List<Integer> authorIds;

    public AuthorIdsDto() {
    }

    public AuthorIdsDto(List<Integer> authorIds) {
        this.authorIds = authorIds;
    }

    public List<Integer> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Integer> authorIds) {
        this.authorIds = authorIds;
    }
}