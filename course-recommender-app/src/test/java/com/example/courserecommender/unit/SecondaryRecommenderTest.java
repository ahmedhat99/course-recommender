package com.example.courserecommender.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.courserecommender.recommender.SecondaryRecommender;
import com.example.recommendercore.RecommendedCourse;

class SecondaryRecommenderTest {

    private SecondaryRecommender recommender;

    @BeforeEach
    void setUp() {
        recommender = new SecondaryRecommender();
    }

    @Test
    void testRecommendedCourses_returnsSecondaryCourses() {
        List<RecommendedCourse> result = recommender.recommendedCourses(List.of());

        assertEquals(2, result.size());
        assertEquals("secondary1", result.get(0).getName());
        assertEquals("secondary2", result.get(1).getName());
    }
}
