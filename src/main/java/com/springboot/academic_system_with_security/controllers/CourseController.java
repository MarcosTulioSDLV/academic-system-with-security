package com.springboot.academic_system_with_security.controllers;

import com.springboot.academic_system_with_security.dtos.CourseRequestDto;
import com.springboot.academic_system_with_security.dtos.CourseResponseDto;
import com.springboot.academic_system_with_security.services.CourseService;
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

@Tag(name = "Course Controller", description = "Controller for managing courses.")
@RestController
@RequestMapping(value = "/api")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(
            summary = "Get all courses",
            description = "This endpoint allows admins and students to retrieve a paginated list of available courses. "
                    + "Both users with the admin role and students can access this endpoint."
    )
    @GetMapping(value = "/courses")
    public ResponseEntity<Page<CourseResponseDto>> getAllCourses(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }

    @Operation(
            summary = "Get course by Id",
            description = "This endpoint allows admins to retrieve information about a specific course by its unique Id. "
                    + "Only users with the admin role can access this endpoint."
    )
    @GetMapping(value = "/courses-by-id/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @Operation(
            summary = "Get courses by professor Id",
            description = "This endpoint allows admins and professors to retrieve a paginated list of courses taught by a specific professor. "
                    + "Admins can access courses of any professor, while professors can only access the list of their own courses."
    )
    @GetMapping(value = "/courses-by-professor-id/{professorId}")
    public ResponseEntity<Page<CourseResponseDto>> getCoursesByProfessorId(@PathVariable Long professorId,
                                                                           @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(courseService.getCoursesByProfessorId(professorId,pageable));
    }

    @Operation(
            summary = "Create a new course",
            description = "This endpoint allows admins to create new courses. "
                    + "Only users with the admin role can access this endpoint."
    )
    @PostMapping(value = "/courses/{professorId}")
    public ResponseEntity<CourseResponseDto> addCourse(@RequestBody @Valid CourseRequestDto courseRequestDto,
                                                       @PathVariable Long professorId){
        return new ResponseEntity<>(courseService.addCourse(courseRequestDto,professorId),HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a course",
            description = "This endpoint allows admins to update course information. "
                    + "Only users with the admin role can access this endpoint."
    )
    @PutMapping(value = "/courses/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(@RequestBody @Valid CourseRequestDto courseRequestDto,
                                                          @PathVariable Long id){
        return ResponseEntity.ok(courseService.updateCourse(courseRequestDto,id));
    }


    @Operation(
            summary = "Delete a course",
            description = "This endpoint allows admins to delete a course. "
                    + "Only users with the admin role can access this endpoint."
    )
    @DeleteMapping(value = "/courses/{id}")
    public ResponseEntity<Object> removeCourse(@PathVariable Long id){
        courseService.removeCourse(id);
        return ResponseEntity.ok("Removed Course with id: "+id+" successfully!");
    }

}
