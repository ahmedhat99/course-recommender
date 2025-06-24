package com.example.courserecommender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.courserecommender.course.Course;
import com.example.courserecommender.course.CourseRepository;
import com.example.courserecommender.course.CourseServiceImpl;
import com.example.courserecommender.exception.ResourceNotFoundException;
import com.example.courserecommender.mapper.CourseMapper;
import com.example.recommendercore.CourseRecommender;
import com.example.recommendercore.RecommendedCourse;

class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseRecommender courseRecommender;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRecommendedCourses_returnsMappedCourses() {

        List<Course> allCourses = List.of(new Course(), new Course());
        List<RecommendedCourse> recommendedInput = List.of(new RecommendedCourse(), new RecommendedCourse());
        List<RecommendedCourse> recommendedOutput = List.of(new RecommendedCourse());
        List<Course> finalCourses = List.of(new Course());

        when(courseRepository.findAll()).thenReturn(allCourses);
        when(courseMapper.toRecommendedCourseList(allCourses)).thenReturn(recommendedInput);
        when(courseRecommender.recommendedCourses(recommendedInput)).thenReturn(recommendedOutput);
        when(courseMapper.toCourseList(recommendedOutput)).thenReturn(finalCourses);

        List<Course> result = courseService.getRecommendedCourses();

        assertEquals(finalCourses, result);
        verify(courseRepository).findAll();
        verify(courseMapper).toRecommendedCourseList(allCourses);
        verify(courseRecommender).recommendedCourses(recommendedInput);
        verify(courseMapper).toCourseList(recommendedOutput);
    }

    @Test
    void testAddCourse_savesAndReturnsCourse() {
        Course inputCourse = new Course();
        Course savedCourse = new Course();

        when(courseRepository.save(inputCourse)).thenReturn(savedCourse);

        Course result = courseService.addCourse(inputCourse);

        assertEquals(savedCourse, result);
        verify(courseRepository).save(inputCourse);
    }

    @Test
    void testUpdateCourse_whenExists_savesAndReturnsCourse() {
        Course inputCourse = new Course();
        Course updatedCourse = new Course();

        when(courseRepository.existsById(anyInt())).thenReturn(true);
        when(courseRepository.save(inputCourse)).thenReturn(updatedCourse);

        Course result = courseService.updateCourse(inputCourse);

        assertEquals(updatedCourse, result);
        verify(courseRepository).existsById(anyInt());
        verify(courseRepository).save(inputCourse);
    }

    @Test
    void testUpdateCourse_whenNotExists_throwsException() {
        Course inputCourse = new Course();
        inputCourse.setId(1);

        when(courseRepository.existsById(1)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> courseService.updateCourse(inputCourse));

        assertEquals("Cannot update. Course with ID 1 not found.", exception.getMessage());
        verify(courseRepository).existsById(1);
        verify(courseRepository, never()).save(any());
    }

    @Test
    void testViewCourse_whenExists_returnsCourse() {
        Course course = new Course();
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));

        Course result = courseService.viewCourse(1);

        assertEquals(course, result);
        verify(courseRepository).findById(1);
    }

    @Test
    void testViewCourse_whenNotExists_throwsException() {
        when(courseRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> courseService.viewCourse(1));

        assertEquals("Course with ID 1 not found.", exception.getMessage());
        verify(courseRepository).findById(1);
    }

    @Test
    void testDeleteCourse_whenExists_deletesCourse() {
        when(courseRepository.existsById(anyInt())).thenReturn(true);

        courseService.deleteCourse(1);

        verify(courseRepository).existsById(1);
        verify(courseRepository).deleteById(1);
    }

    @Test
    void testDeleteCourse_whenNotExists_throwsException() {
        when(courseRepository.existsById(anyInt())).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> courseService.deleteCourse(1));

        assertEquals("Cannot delete. Course with ID 1 not found.", exception.getMessage());
        verify(courseRepository).existsById(1);
        verify(courseRepository, never()).deleteById(any());
    }

    @Test
    void testFindCoursesPaginated_returnsPage() {
        Page<Course> coursePage = new PageImpl<>(List.of());
        when(courseRepository.findAll(any(Pageable.class))).thenReturn(coursePage);

        Page<Course> result = courseService.findCoursesPaginated(0, 2);

        assertEquals(coursePage, result);
        verify(courseRepository).findAll(any(Pageable.class));
    }

}