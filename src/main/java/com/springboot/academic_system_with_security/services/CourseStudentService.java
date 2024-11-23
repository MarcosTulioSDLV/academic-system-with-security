package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseStudentService {

    CourseStudentResponseDto getCourseStudentById(CourseStudentRequestDto courseStudentRequestDto);

    List<CourseResponseDto> getCoursesByStudentId(Long studentId);

    List<CourseStudentWithCourseResponseDto> getCourseStudentWithCourseListByStudentId(Long studentId);

    List<StudentResponseDto> getStudentsByCourseId(Long courseId);

    List<StudentResponseDto> getStudentsByCourseIdAndContainingName(Long courseId, String studentName);

    List<CourseStudentWithStudentResponseDto> getCourseStudentWithStudentListByCourseId(Long courseId);

    @Transactional
    List<CourseStudentResponseDto> addCourseStudent(AddCourseStudentRequestDto addCourseStudentRequestDto);

    @Transactional
    CourseStudentResponseDto setCourseStudentScore(CourseStudentScoreRequestDto courseStudentScoreRequestDto);

    @Transactional
    void removeCourseStudent(CourseStudentRequestDto courseStudentRequestDto);

}
