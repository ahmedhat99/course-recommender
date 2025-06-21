package com.example.courserecommender.dto;

public class CourseDto {
    private String name;
    private String description;
    private int credit;
    private int authorId;

    public CourseDto() {
    }

    public CourseDto(String name, String description, int credit, int authorId) {
        this.name = name;
        this.description = description;
        this.credit = credit;
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}