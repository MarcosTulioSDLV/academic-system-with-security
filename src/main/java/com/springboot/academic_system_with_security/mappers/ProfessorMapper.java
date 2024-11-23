package com.springboot.academic_system_with_security.mappers;

import com.springboot.academic_system_with_security.dtos.*;
import com.springboot.academic_system_with_security.models.Professor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    private final ModelMapper modelMapper;

    private final UserMapper userMapper;

    @Autowired
    public ProfessorMapper(ModelMapper modelMapper, UserMapper userMapper) {
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
    }

    public ProfessorResponseDto toProfessorResponseDto(Professor professor){
        ProfessorResponseDto professorResponseDto= modelMapper.map(professor,ProfessorResponseDto.class);
        UserResponseDto userResponseDto= userMapper.toUserResponseDto(professor.getUser());
        professorResponseDto.setUserResponseDto(userResponseDto);
        return professorResponseDto;
    }

    public Professor toProfessor(ProfessorRequestDto professorRequestDto){
        return modelMapper.map(professorRequestDto,Professor.class);
    }

    public Professor toProfessor(UpdateProfessorRequestDto updateProfessorRequestDto){
        return modelMapper.map(updateProfessorRequestDto,Professor.class);
    }

    public ProfessorWithPasswordResponseDto toProfessorWithPasswordResponseDto(Professor professor){
        ProfessorWithPasswordResponseDto professorWithPasswordResponseDto= modelMapper.map(professor,ProfessorWithPasswordResponseDto.class);
        UserWithPasswordResponseDto userWithPasswordResponseDto= userMapper.toUserWithPasswordResponseDto(professor.getUser());
        professorWithPasswordResponseDto.setUserWithPasswordResponseDto(userWithPasswordResponseDto);
        return professorWithPasswordResponseDto;
    }

}
