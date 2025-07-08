package com.example.courserecommender.recommender;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.recommendercore.CourseRecommender;
import com.example.recommendercore.RecommendedCourse;

@Component("MockServerRecommender")
public class MockServerRecommender implements CourseRecommender {

    private final MockServerClient mockServerClient;

    public MockServerRecommender(MockServerClient mockServerClient) {
        this.mockServerClient = mockServerClient;
    }

    @Override
    public List<RecommendedCourse> recommendedCourses(List<RecommendedCourse> allCourses) {
        return mockServerClient.getRecommendations();
    }
}