package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.UserRequestDto;
import com.springboot.academic_system_with_security.dtos.UserResponseDto;
import com.springboot.academic_system_with_security.models.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    Page<UserResponseDto> getAllUsers(Pageable pageable);

    UserResponseDto getUserById(Long id);

    @Transactional
    UserResponseDto registerUser(UserRequestDto userRequestDto);

    @Transactional
    UserEntity registerUser(UserEntity user,List<Long> roleIds);

    @Transactional
    String loginUser(UserRequestDto userRequestDto);

    @Transactional
    UserResponseDto updateUser(UserRequestDto userRequestDto,Long id);

    @Transactional
    void removeUser(Long id);

}