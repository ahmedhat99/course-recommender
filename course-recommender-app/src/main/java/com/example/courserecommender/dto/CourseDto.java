package com.example.courserecommender.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CourseDto {

    @NotBlank(message = "Course name must not be blank")
    @Size(min = 1, max = 50, message = "Course name must be between 1 and 50 characters")
    private String name;

    @NotBlank(message = "Course description must not be blank")
    @Size(min = 10, max = 150, message = "Description must be between 10 and 150 characters")
    private String description;

    @Min(value = 1, message = "Credit must be at least 1")
    @Max(value = 10, message = "Credit must not exceed 10")
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