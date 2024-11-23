package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.StudentRequestDto;
import com.springboot.academic_system_with_security.dtos.StudentResponseDto;
import com.springboot.academic_system_with_security.dtos.StudentWithPasswordResponseDto;
import com.springboot.academic_system_with_security.dtos.UpdateStudentRequestDto;
import com.springboot.academic_system_with_security.models.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {

    Page<StudentResponseDto> getAllStudents(Pageable pageable);

    StudentResponseDto getStudentById(Long id);

    Student findStudentById(Long id);

    @Transactional
    StudentWithPasswordResponseDto addStudent(StudentRequestDto studentRequestDto);

    @Transactional
    StudentResponseDto updateStudent(UpdateStudentRequestDto updateStudentRequestDto,Long id);

    @Transactional
    void removeStudent(Long id);

}
