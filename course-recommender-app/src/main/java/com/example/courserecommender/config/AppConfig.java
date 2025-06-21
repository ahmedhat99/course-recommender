package com.example.courserecommender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.courserecommender.recommender.PrimaryRecommender;
import com.example.recommendercore.CourseRecommender;

@Configuration
public class AppConfig {
    @Bean
    @Primary
    public CourseRecommender primaryRecommender() {
        return new PrimaryRecommender();
    }
}