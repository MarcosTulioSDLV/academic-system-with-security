package com.springboot.academic_system_with_security.controllers;

import com.springboot.academic_system_with_security.dtos.*;
import com.springboot.academic_system_with_security.services.ProfessorService;
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

@Tag(name = "Professor Controller", description = "Controller for managing professor profiles and professor registrations.")
@RestController
@RequestMapping(value = "/api")
public class ProfessorController {

    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @Operation(
            summary = "Get all professors",
            description = "This endpoint allows the admin to retrieve a paginated list of all professors. "
                    + "Only users with the admin role can access this endpoint."
    )
    @GetMapping(value = "/professors")
    public ResponseEntity<Page<ProfessorResponseDto>> getAllProfessors(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(professorService.getAllProfessors(pageable));
    }

    @Operation(
            summary = "Get professor by Id",
            description = "This endpoint allows professors and admins to retrieve a professor's profile by their unique Id. "
                    + "Professors can access only their own profile, while admins can access any professor's profile."
    )
    @GetMapping(value = "/professors-by-id/{id}")
    public ResponseEntity<ProfessorResponseDto> getProfessorById(@PathVariable Long id){
        return ResponseEntity.ok(professorService.getProfessorById(id));
    }

    @Operation(
            summary = "Register a new professor",
            description = "This endpoint allows admins to register new professors. "
                    + "Only users with the admin role can access this endpoint."
    )
    @PostMapping(value = "/professors-register")
    public ResponseEntity<ProfessorWithPasswordResponseDto> addProfessor(@RequestBody @Valid ProfessorRequestDto professorRequestDto){
        return new ResponseEntity<>(professorService.addProfessor(professorRequestDto),HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a professor",
            description = "This endpoint allows updating a professor's profile. "
                    + "Professors can only modify their own information, while admins can modify any professor's profile."
    )
    @PutMapping(value = "/professors/{id}")
    public ResponseEntity<ProfessorResponseDto> updateProfessor(@RequestBody @Valid UpdateProfessorRequestDto updateProfessorRequestDto,
                                                                @PathVariable Long id){
        return ResponseEntity.ok(professorService.updateProfessor(updateProfessorRequestDto,id));
    }

    //Note:For Remove a Professor, Remove the associated User and then the Professor will be removed.
    @Operation(
            summary = "Delete a professor",
            description = "This endpoint allows admins to delete a professor profile. "
                    + "Only users with the admin role can access this endpoint."
    )
    @DeleteMapping(value = "/professors/{id}")
    public ResponseEntity<String> removeProfessor(@PathVariable Long id){
        professorService.removeProfessor(id);
        return ResponseEntity.ok("Professor removed successfully!");
    }

}
