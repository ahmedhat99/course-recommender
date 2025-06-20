package com.example.recommendercore;

public class Course {

    private int id;
    private String name;
    private String description;
    private int credit;
    private int authorId;

    public Course(int id, String name, String description, int credit, int authorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.credit = credit;
        this.authorId = authorId;
    }

    public Course(String name) {
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCredit() {
        return credit;
    }

    public int getAuthorId() {
        return authorId;
    }
}
