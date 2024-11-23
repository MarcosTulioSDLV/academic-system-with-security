package com.springboot.academic_system_with_security.mappers;

import com.springboot.academic_system_with_security.dtos.*;
import com.springboot.academic_system_with_security.models.CourseStudent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseStudentMapper {

    private final ModelMapper modelMapper;

    private final CourseMapper courseMapper;

    private final StudentMapper studentMapper;

    @Autowired
    public CourseStudentMapper(ModelMapper modelMapper, CourseMapper courseMapper, StudentMapper studentMapper) {
        this.modelMapper = modelMapper;
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
    }

    public CourseStudentResponseDto toCourseStudentResponseDto(CourseStudent courseStudent){
        CourseStudentResponseDto courseStudentResponseDto= modelMapper.map(courseStudent,CourseStudentResponseDto.class);
        CourseResponseDto courseResponseDto= courseMapper.toCourseResponseDto(courseStudent.getCourse());
        StudentResponseDto studentResponseDto= studentMapper.toStudentResponseDto(courseStudent.getStudent());
        courseStudentResponseDto.setCourseResponseDto(courseResponseDto);
        courseStudentResponseDto.setStudentResponseDto(studentResponseDto);
        return courseStudentResponseDto;
    }

    public CourseStudentWithCourseResponseDto toCourseStudentWithCourseResponseDto(CourseStudent courseStudent){
        CourseStudentWithCourseResponseDto courseStudentWithCourseResponseDto = modelMapper.map(courseStudent,CourseStudentWithCourseResponseDto.class);
        CourseResponseDto courseResponseDto= courseMapper.toCourseResponseDto(courseStudent.getCourse());
        courseStudentWithCourseResponseDto.setCourseResponseDto(courseResponseDto);
        return courseStudentWithCourseResponseDto;
    }

    public CourseStudentWithStudentResponseDto toCourseStudentWithStudentResponseDto(CourseStudent courseStudent){
        CourseStudentWithStudentResponseDto courseStudentWithStudentResponseDto= modelMapper.map(courseStudent,CourseStudentWithStudentResponseDto.class);
        StudentResponseDto studentResponseDto= studentMapper.toStudentResponseDto(courseStudent.getStudent());
        courseStudentWithStudentResponseDto.setStudentResponseDto(studentResponseDto);
        return courseStudentWithStudentResponseDto;
    }
}
