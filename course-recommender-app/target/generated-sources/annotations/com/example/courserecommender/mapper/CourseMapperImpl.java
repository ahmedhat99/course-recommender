package com.example.courserecommender.mapper;

import com.example.courserecommender.course.Course;
import com.example.courserecommender.dto.CourseDto;
import com.example.recommendercore.RecommendedCourse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-06T03:15:03+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public RecommendedCourse toRecommendedCourse(Course course) {
        if ( course == null ) {
            return null;
        }

        RecommendedCourse recommendedCourse = new RecommendedCourse();

        recommendedCourse.setCredit( course.getCredit() );
        recommendedCourse.setDescription( course.getDescription() );
        recommendedCourse.setId( course.getId() );
        recommendedCourse.setName( course.getName() );

        return recommendedCourse;
    }

    @Override
    public Course toCourse(RecommendedCourse recommendedCourse) {
        if ( recommendedCourse == null ) {
            return null;
        }

        Course course = new Course();

        course.setId( recommendedCourse.getId() );
        course.setName( recommendedCourse.getName() );
        course.setDescription( recommendedCourse.getDescription() );
        course.setCredit( recommendedCourse.getCredit() );

        return course;
    }

    @Override
    public List<RecommendedCourse> toRecommendedCourseList(List<Course> courses) {
        if ( courses == null ) {
            return null;
        }

        List<RecommendedCourse> list = new ArrayList<RecommendedCourse>( courses.size() );
        for ( Course course : courses ) {
            list.add( toRecommendedCourse( course ) );
        }

        return list;
    }

    @Override
    public List<Course> toCourseList(List<RecommendedCourse> recommendedCourses) {
        if ( recommendedCourses == null ) {
            return null;
        }

        List<Course> list = new ArrayList<Course>( recommendedCourses.size() );
        for ( RecommendedCourse recommendedCourse : recommendedCourses ) {
            list.add( toCourse( recommendedCourse ) );
        }

        return list;
    }

    @Override
    public Course toCourse(int id, CourseDto dto) {
        if ( dto == null ) {
            return null;
        }

        Course course = new Course();

        if ( dto != null ) {
            course.setName( dto.getName() );
            course.setDescription( dto.getDescription() );
            course.setCredit( dto.getCredit() );
        }
        course.setId( id );

        return course;
    }

    @Override
    public Course toCourse(CourseDto dto) {
        if ( dto == null ) {
            return null;
        }

        Course course = new Course();

        course.setName( dto.getName() );
        course.setDescription( dto.getDescription() );
        course.setCredit( dto.getCredit() );

        return course;
    }
}
