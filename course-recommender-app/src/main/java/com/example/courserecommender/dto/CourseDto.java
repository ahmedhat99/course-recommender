package com.example.courserecommender.dto;

public class CourseDto {
    private String name;
    private String description;
    private int credit;

    public CourseDto() {
    }

    public CourseDto(String name, String description, int credit) {
        this.name = name;
        this.description = description;
        this.credit = credit;
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
}