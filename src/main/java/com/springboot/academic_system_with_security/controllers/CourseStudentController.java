package com.springboot.academic_system_with_security.controllers;

import com.springboot.academic_system_with_security.dtos.*;
import com.springboot.academic_system_with_security.services.CourseStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Course-Student Controller", description = "Controller for managing student enrollments in courses.")
@RestController
@RequestMapping(value = "/api")
public class CourseStudentController {

    private final CourseStudentService courseStudentService;

    public CourseStudentController(CourseStudentService courseStudentService) {
        this.courseStudentService = courseStudentService;
    }

    @Operation(
            summary = "Get course-student records by Id",
            description = "This endpoint allows admins to retrieve course-student records by their unique Id. "
                    + "Only users with the admin role can access this endpoint."
    )
    @GetMapping(value = "/course-students-by-id")
    public ResponseEntity<CourseStudentResponseDto> getCourseStudentById(@RequestBody @Valid CourseStudentRequestDto courseStudentRequestDto){
        return ResponseEntity.ok(courseStudentService.getCourseStudentById(courseStudentRequestDto));
    }

    //Note: Return the courses, but not other data such as scores.
    @Operation(
            summary = "Get courses for a student",
            description = "This endpoint allows students to view the list of courses they are enrolled in. "
                    + "Students can access their course information, while admins can access any student's courses."
    )
    @GetMapping(value = "/students/{studentId}/courses")
    public ResponseEntity<List<CourseResponseDto>> getCoursesByStudentId(@PathVariable Long studentId){
        return ResponseEntity.ok(courseStudentService.getCoursesByStudentId(studentId));
    }

    //Note: In addition to returning the courses, also return the scores (it could even return another attributes of the CourseStudent class).
    //Note: A CourseStudentWithCourseResponseDto was required, because not all attributes of CourseStudentResponseDto are returned (Course is required).
    @Operation(
            summary = "Get course details for a student",
            description = "This endpoint allows students to view detailed information about the courses they are enrolled in, "
                    + "including scores. "
                    + "Admins can view this information for all students, while students can only view their own course details."
    )
    @GetMapping(value = "/course-students/{studentId}/details-course")
    public ResponseEntity<List<CourseStudentWithCourseResponseDto>> getCourseStudentsWithCourseByStudentId(@PathVariable Long studentId){
        return ResponseEntity.ok(courseStudentService.getCourseStudentWithCourseListByStudentId(studentId));
    }

    @Operation(
            summary = "Get students enrolled in a course",
            description = "This endpoint allows professors to view the list of students enrolled in the courses they teach. "
                    + "Professors can only access the students of their own courses, while admins can access any course's student list."
    )
    @GetMapping(value = "/courses/{courseId}/students")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByCourseId(@PathVariable Long courseId){
        return ResponseEntity.ok(courseStudentService.getStudentsByCourseId(courseId));
    }

    @Operation(
            summary = "Get students enrolled in a course by name",
            description = "This endpoint allows professors to search for students by name within the any specific courses they teach. "
                    + "Professors can only search within their courses, while admins can search across all courses."
    )
    @GetMapping(value = "/courses/{courseId}/students-containing-name")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByCourseIdAndContainingName(@PathVariable Long courseId,
                                                                                           @RequestParam String studentName){
        return ResponseEntity.ok(courseStudentService.getStudentsByCourseIdAndContainingName(courseId,studentName));
    }

    //Note: In addition to returning the courses, also return the scores (it could even return another attributes of the CourseStudent class).
    //Note: A CourseStudentWithStudentResponseDto was required, because not all attributes of CourseStudentResponseDto are returned (Student is required).
    @Operation(
            summary = "Get detailed student information by course",
            description = "This endpoint allows admins and professors to view detailed information, including scores, about students enrolled in a specific course. "
                    + "Professors can view students in their own courses, while admins can access student information across all courses."
    )
    @GetMapping(value = "/course-students/{courseId}/details-student")
    public ResponseEntity<List<CourseStudentWithStudentResponseDto>> getCourseStudentWithStudentListByCourseId(@PathVariable Long courseId){
        return ResponseEntity.ok(courseStudentService.getCourseStudentWithStudentListByCourseId(courseId));
    }

    @Operation(
            summary = "Enroll a student in courses",
            description = "This endpoint allows students to enroll in courses. "
                    + "Students can enroll themselves in courses; admins can enroll any student in courses."
    )
    @PostMapping(value = "/course-students")
    public ResponseEntity<List<CourseStudentResponseDto>> addCourseStudent(@RequestBody @Valid AddCourseStudentRequestDto addCourseStudentRequestDto,
                                                                           @PageableDefault(size = 10) Pageable pageable){
        return new ResponseEntity<>(courseStudentService.addCourseStudent(addCourseStudentRequestDto),HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a student score in a course",
            description = "This endpoint allows admins and professors to update the scores of students enrolled in a course. "
                    + "Professors can update student scores for their courses, while admins can update any student score."
    )
    @PutMapping(value = "/course-students-score")
    public ResponseEntity<CourseStudentResponseDto> setCourseStudentScore(@RequestBody @Valid CourseStudentScoreRequestDto courseStudentScoreRequestDto){
        return ResponseEntity.ok(courseStudentService.setCourseStudentScore(courseStudentScoreRequestDto));
    }

    @Operation(
            summary = "Unenroll a student from a course",
            description = "This endpoint allows students to unenroll from courses they no longer wish to attend. "
                    + "Students can unenroll themselves from their courses; admins can unenroll any student from any course."
    )
    @DeleteMapping(value = "/course-students")
    public ResponseEntity<String> removeCourseStudent(@RequestBody @Valid CourseStudentRequestDto courseStudentRequestDto){
        courseStudentService.removeCourseStudent(courseStudentRequestDto);
        String message= String.format("Course x Student: (Course id: %d, Student id: %d) removed successfully!",courseStudentRequestDto.getCourseId(),courseStudentRequestDto.getStudentId());
        return ResponseEntity.ok(message);
    }

}
