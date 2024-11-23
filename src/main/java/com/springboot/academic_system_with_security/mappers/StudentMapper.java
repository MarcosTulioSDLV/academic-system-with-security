package com.springboot.academic_system_with_security.mappers;

import com.springboot.academic_system_with_security.dtos.*;
import com.springboot.academic_system_with_security.models.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    private final ModelMapper modelMapper;

    private final UserMapper userMapper;

    @Autowired
    public StudentMapper(ModelMapper modelMapper, UserMapper userMapper) {
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
    }

    public StudentResponseDto toStudentResponseDto(Student student){
        StudentResponseDto studentResponseDto= modelMapper.map(student,StudentResponseDto.class);
        UserResponseDto userResponseDto= userMapper.toUserResponseDto(student.getUser());
        studentResponseDto.setUserResponseDto(userResponseDto);
        return studentResponseDto;
    }

    public Student toStudent(StudentRequestDto studentRequestDto){
        return modelMapper.map(studentRequestDto,Student.class);
    }

    public Student toStudent(UpdateStudentRequestDto updateStudentRequestDto){
        return modelMapper.map(updateStudentRequestDto,Student.class);
    }

    public StudentWithPasswordResponseDto toStudentWithPasswordResponseDto(Student student) {
        StudentWithPasswordResponseDto studentWithPasswordResponseDto= modelMapper.map(student,StudentWithPasswordResponseDto.class);
        UserWithPasswordResponseDto userWithPasswordResponseDto= userMapper.toUserWithPasswordResponseDto(student.getUser());
        studentWithPasswordResponseDto.setUserWithPasswordResponseDto(userWithPasswordResponseDto);
        return studentWithPasswordResponseDto;
    }

}
