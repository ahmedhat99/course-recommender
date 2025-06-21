package com.example.courserecommender.course;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.recommendercore.Course;

@Repository
public class JdbcCourseRepository implements CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addCourse(Course course) {
        String sql = "INSERT INTO Course (id, name, description, credit, author_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getCredit(),
                course.getAuthorId());
    }

    @Override
    public void updateCourse(Course course) {
        String sql = "UPDATE Course SET name = ?, description = ?, credit = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                course.getName(),
                course.getDescription(),
                course.getCredit(),
                course.getId());
    }

    @Override
    public Course viewCourse(int id) {
        String sql = "SELECT * FROM Course WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Course(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("credit"),
                    rs.getInt("author_id")), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteCourse(int id) {
        String sql = "DELETE FROM Course WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Course> findAll() {
        String sql = "SELECT * FROM Course";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Course(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("credit"),
                rs.getInt("author_id")));
    }

}