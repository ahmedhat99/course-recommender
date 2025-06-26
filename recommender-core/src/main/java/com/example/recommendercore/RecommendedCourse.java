package com.example.recommendercore;

public class RecommendedCourse {
    private int id;
    private String name;
    private String description;
    private int credit;

    public RecommendedCourse() {
    }

    public RecommendedCourse(String name, String description, int credit) {
        this.name = name;
        this.description = description;
        this.credit = credit;
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

}