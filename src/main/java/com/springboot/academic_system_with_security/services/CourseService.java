package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.CourseRequestDto;
import com.springboot.academic_system_with_security.dtos.CourseResponseDto;
import com.springboot.academic_system_with_security.models.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {

    Page<CourseResponseDto> getAllCourses(Pageable pageable);

    CourseResponseDto getCourseById(Long id);

    Course findCourseById(Long id);

    Page<CourseResponseDto> getCoursesByProfessorId(Long professorId,Pageable pageable);

    @Transactional
    CourseResponseDto addCourse(CourseRequestDto courseRequestDto,Long professorId);

    @Transactional
    CourseResponseDto updateCourse(CourseRequestDto courseRequestDto,Long id);

    @Transactional
    void removeCourse(Long id);
}
