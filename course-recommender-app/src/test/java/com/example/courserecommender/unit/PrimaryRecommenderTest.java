package com.example.courserecommender.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.courserecommender.recommender.PrimaryRecommender;
import com.example.recommendercore.RecommendedCourse;

class PrimaryRecommenderTest {

    private PrimaryRecommender recommender;

    @BeforeEach
    void setUp() {
        recommender = new PrimaryRecommender();
    }

    @Test
    void testRecommendedCourses_returnsPrimaryCourses() {
        List<RecommendedCourse> result = recommender.recommendedCourses(List.of());

        assertEquals(2, result.size());
        assertEquals("primary1", result.get(0).getName());
        assertEquals("primary2", result.get(1).getName());
    }
}
