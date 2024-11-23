package com.springboot.academic_system_with_security.controllers;

import com.springboot.academic_system_with_security.dtos.RoleRequestDto;
import com.springboot.academic_system_with_security.dtos.RoleResponseDto;
import com.springboot.academic_system_with_security.services.RoleService;
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

@Tag(name = "Role Controller", description = "Controller for managing roles.")
@RestController
@RequestMapping(value = "/api")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(
            summary = "Get all roles",
            description = "Retrieve a paginated list of all available roles. Only accessible by admins."
    )
    @GetMapping(value = "/roles")
    public ResponseEntity<Page<RoleResponseDto>> getAllRoles(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(roleService.getAllRoles(pageable));
    }

    @Operation(
            summary = "Get a role by Id",
            description = "Retrieve a specific role by its Id. Only accessible by admins."
    )
    @GetMapping(value = "/roles-by-id/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Long id){
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    /*
      @Operation(
            summary = "Create a new role",
            description = "Allows admins to create a new role. Only accessible by admins."
    )
    @PostMapping(value = "/roles")
    public ResponseEntity<RoleResponseDto> addRole(@RequestBody @Valid RoleRequestDto roleRequestDto){
        return new ResponseEntity<>(roleService.addRole(roleRequestDto),HttpStatus.CREATED);
    }*/

}
