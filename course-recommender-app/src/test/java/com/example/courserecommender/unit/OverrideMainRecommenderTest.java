package com.example.courserecommender.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.courserecommender.recommender.OverrideMainRecommender;
import com.example.recommendercore.RecommendedCourse;

class OverrideMainRecommenderTest {

    private OverrideMainRecommender recommender;

    @BeforeEach
    void setUp() {
        recommender = new OverrideMainRecommender();
    }

    @Test
    void testRecommendedCourses_whenCreditAboveThresholdExists_returnsFilteredCourses() {
        List<RecommendedCourse> input = List.of(
                new RecommendedCourse("Test 1", "Description", 4),
                new RecommendedCourse("Test 2", "Description", 2));

        List<RecommendedCourse> result = recommender.recommendedCourses(input);

        assertEquals(1, result.size());
        assertEquals("Test 1", result.get(0).getName());
    }

    @Test
    void testRecommendedCourses_whenAllCoursesCreditBelowThreshold_returnsEmptyList() {
        List<RecommendedCourse> input = List.of(
                new RecommendedCourse("Test 1", "Description", 2),
                new RecommendedCourse("Test 2", "Description", 2));

        List<RecommendedCourse> result = recommender.recommendedCourses(input);

        assertTrue(result.isEmpty());
    }
}