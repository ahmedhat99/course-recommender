package com.example.courserecommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.recommendercore",
        "com.example.courserecommender" }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.example.recommendercore.MainRecommender.class))
public class CourseRecommenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseRecommenderApplication.class, args);
    }

}