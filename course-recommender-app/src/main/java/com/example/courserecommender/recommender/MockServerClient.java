package com.example.courserecommender.recommender;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.recommendercore.RecommendedCourse;

@FeignClient(name = "mockServerClient", url = "http://localhost:8089")
public interface MockServerClient {
    @GetMapping("/recommendations")
    List<RecommendedCourse> getRecommendations();
}