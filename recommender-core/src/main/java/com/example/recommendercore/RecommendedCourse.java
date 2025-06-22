package com.example.recommendercore;

public class RecommendedCourse {
    private int id;
    private String name;
    private String description;
    private int credit;
    private int authorId;

    public RecommendedCourse() {
    }

    public RecommendedCourse(int id, String name, String description, int credit, int authorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.credit = credit;
        this.authorId = authorId;
    }

    public RecommendedCourse(String name) {
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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