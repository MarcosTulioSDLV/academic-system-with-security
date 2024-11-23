package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.ProfessorRequestDto;
import com.springboot.academic_system_with_security.dtos.ProfessorResponseDto;
import com.springboot.academic_system_with_security.dtos.ProfessorWithPasswordResponseDto;
import com.springboot.academic_system_with_security.dtos.UpdateProfessorRequestDto;
import com.springboot.academic_system_with_security.models.Professor;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProfessorService {

    Page<ProfessorResponseDto> getAllProfessors(Pageable pageable);

    ProfessorResponseDto getProfessorById(Long id);

    Professor findProfessorById(Long id);

    @Transactional
    ProfessorWithPasswordResponseDto addProfessor(ProfessorRequestDto professorRequestDto);

    @Transactional
    ProfessorResponseDto updateProfessor(UpdateProfessorRequestDto updateProfessorRequestDto,Long id);

    @Transactional
    void removeProfessor(Long id);

}
