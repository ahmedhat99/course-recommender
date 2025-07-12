package com.example.courserecommender.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.courserecommender.course.Course;
import com.example.courserecommender.dto.CourseDto;
import com.example.generated.RecommendedCourseType;
import com.example.recommendercore.RecommendedCourse;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    RecommendedCourse toRecommendedCourse(Course course);

    Course toCourse(RecommendedCourse recommendedCourse);

    List<RecommendedCourse> toRecommendedCourseList(List<Course> courses);

    List<Course> toCourseList(List<RecommendedCourse> recommendedCourses);

    @Mapping(target = "id", source = "id")
    Course toCourse(int id, CourseDto dto);

    Course toCourse(CourseDto dto);

    RecommendedCourse fromGenerated(RecommendedCourseType source);

    List<RecommendedCourse> fromGeneratedList(List<RecommendedCourseType> list);
}
