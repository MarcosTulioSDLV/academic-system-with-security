package com.springboot.academic_system_with_security.controllers;

import com.springboot.academic_system_with_security.dtos.StudentRequestDto;
import com.springboot.academic_system_with_security.dtos.StudentResponseDto;
import com.springboot.academic_system_with_security.dtos.StudentWithPasswordResponseDto;
import com.springboot.academic_system_with_security.dtos.UpdateStudentRequestDto;
import com.springboot.academic_system_with_security.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Student Controller", description = "Controller for managing student profiles and student registrations.")
@RestController
@RequestMapping(value = "/api")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(
            summary = "Get all students",
            description = "This endpoint allows the admin to retrieve a paginated list of all students. "
                    + "Only users with the admin role can access this endpoint."
    )
    @GetMapping(value = "/students")
    public ResponseEntity<Page<StudentResponseDto>> getAllStudents(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(studentService.getAllStudents(pageable));
    }

    @Operation(
            summary = "Get student by Id",
            description = "This endpoint allows students and admins to retrieve a student's profile by their unique Id. "
                    + "Students can access only their own profile, while admins can access any student's profile."
    )
    @GetMapping(value = "/students-by-id/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @Operation(
            summary = "Register a new student",
            description = "This endpoint allows admins to register new students. "
                    + "Only users with the admin role can access this endpoint."
    )
    @PostMapping(value = "/students-register")
    public ResponseEntity<StudentWithPasswordResponseDto> addStudent(@RequestBody @Valid StudentRequestDto studentRequestDto){
        return new ResponseEntity<>(studentService.addStudent(studentRequestDto),HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a student",
            description = "This endpoint allows updating a student's profile. "
                    + "Students can only modify their own information, while admins can modify any student's profile."
    )
    @PutMapping(value = "/students/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@RequestBody @Valid UpdateStudentRequestDto updateStudentRequestDto,
                                                            @PathVariable Long id){
        return ResponseEntity.ok(studentService.updateStudent(updateStudentRequestDto,id));
    }

    //Note:For Remove a Student, Remove the associated User and then the Student will be removed.
    @Operation(
            summary = "Delete a student",
            description = "This endpoint allows admins to delete a student profile. "
                    + "Only users with the admin role can access this endpoint."
    )
    @DeleteMapping(value = "/students/{id}")
    public ResponseEntity<String> removeStudent(@PathVariable Long id){
        studentService.removeStudent(id);
        return ResponseEntity.ok("Student removed successfully!");
    }

}
