package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.*;
import com.springboot.academic_system_with_security.enums.RoleName;
import com.springboot.academic_system_with_security.exceptions.*;
import com.springboot.academic_system_with_security.mappers.ProfessorMapper;
import com.springboot.academic_system_with_security.models.Professor;
import com.springboot.academic_system_with_security.models.UserEntity;
import com.springboot.academic_system_with_security.repository.ProfessorRepository;
import com.springboot.academic_system_with_security.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorServiceImp implements ProfessorService{

    private final ProfessorRepository professorRepository;

    private final ProfessorMapper professorMapper;

    private final UserService userService;

    private final StudentRepository studentRepository;

    private final RoleService roleService;


    @Autowired
    public ProfessorServiceImp(ProfessorRepository professorRepository, ProfessorMapper professorMapper, UserService userService, StudentRepository studentRepository, RoleService roleService) {
        this.professorRepository = professorRepository;
        this.professorMapper = professorMapper;
        this.userService = userService;
        this.studentRepository = studentRepository;
        this.roleService = roleService;
    }

    @Override
    public Page<ProfessorResponseDto> getAllProfessors(Pageable pageable) {
        return professorRepository.findAll(pageable).map(professorMapper::toProfessorResponseDto);
    }

    @Override
    public ProfessorResponseDto getProfessorById(Long id) {
        Professor professor= findProfessorById(id);
        return professorMapper.toProfessorResponseDto(professor);
    }

    @Override
    public Professor findProfessorById(Long id) {
        return professorRepository.findById(id).orElseThrow(() -> new ProfessorNotFoundException("Professor with id: " + id + " not found!"));
    }

    @Override
    @Transactional
    public ProfessorWithPasswordResponseDto addProfessor(ProfessorRequestDto professorRequestDto) {
        Professor professor= professorMapper.toProfessor(professorRequestDto);
        validateUniqueFields(professor);

        //Add inner UserEntity
        //------
        UserEntity user= new UserEntity();
        user.setUsername(professorRequestDto.getUsername());
        String password= generateRandomPassword();
        user.setPassword(password);
        Long roleProfessorId= roleService.findRoleByRoleName(RoleName.ROLE_PROFESSOR).getId();

        user= userService.registerUser(user,List.of(roleProfessorId));
        //------

        professor.setUser(user);
        professor= professorRepository.save(professor);

        ProfessorWithPasswordResponseDto professorWithPasswordResponseDto= professorMapper.toProfessorWithPasswordResponseDto(professor);
        professorWithPasswordResponseDto.getUserWithPasswordResponseDto().setPassword(password);//Note:Set the decode password (the password retrieved from the DB is encoded)
        return professorWithPasswordResponseDto;
    }

    private String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(12);//Password length 12 with letters and numbers
    }

    private void validateUniqueFields(Professor professor) {
        validateUniqueFieldsInProfessor(professor);
        validateUniqueFieldsInStudent(professor);
    }

    private void validateUniqueFieldsInProfessor(Professor professor) {
        //Validate unique fields in Professor entity
        if(professorRepository.existsByDocument(professor.getDocument())){
            throw new ProfessorDocumentExistsException("Professor with Document: "+ professor.getDocument()+" already exists!");
        }
        if(professorRepository.existsByPhoneNumber(professor.getPhoneNumber())){
            throw new ProfessorPhoneNumberExistsException("Professor with Phone Number: "+ professor.getPhoneNumber()+" already exists!");
        }
        if(professorRepository.existsByEmailIgnoreCase(professor.getEmail())){
            throw new ProfessorEmailExistsException("Professor with Email: "+ professor.getEmail()+" already exists!");
        }
    }

    private void validateUniqueFieldsInStudent(Professor professor) {
        //Validate unique fields in Student entity
        if(studentRepository.existsByDocument(professor.getDocument())){
            throw new StudentDocumentExistsException("Student with Document: "+ professor.getDocument()+" already exists!");
        }
        if(studentRepository.existsByEmailIgnoreCase(professor.getEmail())){
            throw new StudentEmailExistsException("Student with Email: "+ professor.getEmail()+" already exists!");
        }
    }

    @Override
    @Transactional
    public ProfessorResponseDto updateProfessor(UpdateProfessorRequestDto updateProfessorRequestDto,Long id) {
        Professor professor= professorMapper.toProfessor(updateProfessorRequestDto);
        professor.setId(id);

        Professor recoveredProfessor= findProfessorById(id);
        validateFieldsUpdateConflict(professor,recoveredProfessor);

        //Update inner UserEntity
        UserResponseDto userResponseDto= userService.updateUser(
                updateProfessorRequestDto.getUserRequestDto(),
                recoveredProfessor.getUser().getId()
        );

        BeanUtils.copyProperties(professor,recoveredProfessor,"user");
        return professorMapper.toProfessorResponseDto(recoveredProfessor);
    }

    private void validateFieldsUpdateConflict(Professor professor,Professor recoveredProfessor) {
        if(professorDocumentExistsAndBelongsToAnotherInstance(professor.getDocument(),recoveredProfessor)){
            throw new ProfessorDocumentExistsException("Professor with Document: "+ professor.getDocument()+" already exists!");
        }
        if(professorPhoneNumberExistsAndBelongsToAnotherInstance(professor.getPhoneNumber(),recoveredProfessor)){
            throw new ProfessorPhoneNumberExistsException("Professor with Phone Number: "+ professor.getPhoneNumber()+" already exists!");
        }
        if(professorEmailExistsAndBelongsToAnotherInstance(professor.getEmail(),recoveredProfessor)){
            throw new ProfessorEmailExistsException("Professor with Email: "+professor.getEmail()+" already exists!");
        }
        validateUniqueFieldsInStudent(professor);
    }

    private boolean professorDocumentExistsAndBelongsToAnotherInstance(String document,Professor recoveredProfessor) {
        return professorRepository.existsByDocument(document) && !document.equals(recoveredProfessor.getDocument());
    }
    private boolean professorPhoneNumberExistsAndBelongsToAnotherInstance(String phoneNumber,Professor recoveredProfessor) {
        return professorRepository.existsByPhoneNumber(phoneNumber) && !phoneNumber.equals(recoveredProfessor.getPhoneNumber());
    }
    private boolean professorEmailExistsAndBelongsToAnotherInstance(String email,Professor recoveredProfessor) {
        return professorRepository.existsByEmailIgnoreCase(email) && !email.equalsIgnoreCase(recoveredProfessor.getEmail());
    }

    @Override
    @Transactional
    public void removeProfessor(Long id) {
        UserEntity user= findProfessorById(id).getUser();
        userService.removeUser(user.getId());
    }

}

