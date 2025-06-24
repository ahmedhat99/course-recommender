package com.example.courserecommender.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class AuthorDto {

    @NotBlank(message = "Author name must not be blank")
    @Size(min = 1, max = 50, message = "Author name must be between 1 and 50 characters")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid format")
    @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters")
    private String email;
    
    @NotNull(message = "Birthdate must be provided")
    @Past(message = "Birthdate must be a past date")
    private LocalDate birthdate;

    public AuthorDto() {
    }

    public AuthorDto(String name, String email, LocalDate birthdate) {
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}
