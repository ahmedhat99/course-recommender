package com.example.courserecommender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;

import com.example.courserecommender.recommender.PrimaryRecommender;
import com.example.recommendercore.CourseRecommender;

@Configuration
@ComponentScan(basePackages = { "com.example.recommendercore",
        "com.example.courserecommender" }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.example.recommendercore.MainRecommender.class))
public class AppConfig {
    @Bean
    @Primary
    public CourseRecommender primaryRecommender() {
        return new PrimaryRecommender();
    }
}