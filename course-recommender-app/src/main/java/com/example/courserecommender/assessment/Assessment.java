package com.example.courserecommender.assessment;

import com.example.courserecommender.course.Course;

import jakarta.persistence.*;

@Entity
@Table(name = "Assessment")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    @OneToOne
    @JoinColumn(name = "course_id", unique = true)
    private Course course;

    public Assessment() {
    }

    public Assessment(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
