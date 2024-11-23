package com.springboot.academic_system_with_security.mappers;

import com.springboot.academic_system_with_security.dtos.CourseRequestDto;
import com.springboot.academic_system_with_security.dtos.CourseResponseDto;
import com.springboot.academic_system_with_security.dtos.ProfessorResponseDto;
import com.springboot.academic_system_with_security.models.Course;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    private final ModelMapper modelMapper;

    private final ProfessorMapper professorMapper;

    @Autowired
    public CourseMapper(ModelMapper modelMapper, ProfessorMapper professorMapper) {
        this.modelMapper = modelMapper;
        this.professorMapper = professorMapper;
    }


    public CourseResponseDto toCourseResponseDto(Course course){
        CourseResponseDto courseResponseDto= modelMapper.map(course,CourseResponseDto.class);
        ProfessorResponseDto professorResponseDto= professorMapper.toProfessorResponseDto(course.getProfessor());
        courseResponseDto.setProfessorResponseDto(professorResponseDto);
        return  courseResponseDto;
    }

    public Course toCourse(CourseRequestDto courseRequestDto){
        return modelMapper.map(courseRequestDto,Course.class);
    }

}
