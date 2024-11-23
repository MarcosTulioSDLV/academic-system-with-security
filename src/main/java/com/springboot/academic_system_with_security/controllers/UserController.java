package com.springboot.academic_system_with_security.controllers;

import com.springboot.academic_system_with_security.dtos.UserRequestDto;
import com.springboot.academic_system_with_security.dtos.UserResponseDto;
import com.springboot.academic_system_with_security.services.UserService;
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

@Tag(name = "User Controller", description = "Controller that provides a login endpoint for all users and enables admin users to register other users and manage profiles.")
@RestController
@RequestMapping(value = "/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get all users' profiles",
            description = "Only admins can view all users' profiles, regardless of their role.")
    @GetMapping(value = "/users")
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @Operation(
            summary = "Get a specific user's profile by Id",
            description = "Only admins can view the details of a specific user by their Id, regardless of their role.")
    @GetMapping(value = "/users-by-id/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "Register a new admin user",
            description = "Only admins can register new admin users.")
    @PostMapping(value = "/users-admin-register")
    public ResponseEntity<UserResponseDto> registerUserAdmin(@RequestBody @Valid UserRequestDto userRequestDto){
        return new ResponseEntity<>(userService.registerUser(userRequestDto),HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login a user",
            description = "Public endpoint for user login, accessible to all users.")
    @PostMapping(value = "/users-login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid UserRequestDto userRequestDto){
        String jwtToken= userService.loginUser(userRequestDto);
        return new ResponseEntity<>(jwtToken,HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a user's profile",
            description = "Only admins can update the profiles of other users, regardless of their role.")
    @PutMapping(value = "/users/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody @Valid UserRequestDto userRequestDto,
                                                      @PathVariable Long id){
        return ResponseEntity.ok(userService.updateUser(userRequestDto,id));
    }

    @Operation(
            summary = "Delete a user's profile",
            description = "Only admins can delete the profiles of other users, regardless of their role.")
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Object> removeUser(@PathVariable Long id){
        userService.removeUser(id);
        return ResponseEntity.ok("User Removed successfully");
    }

}
