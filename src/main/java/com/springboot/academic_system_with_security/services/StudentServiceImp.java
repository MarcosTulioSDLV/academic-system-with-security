package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.*;
import com.springboot.academic_system_with_security.enums.RoleName;
import com.springboot.academic_system_with_security.exceptions.*;
import com.springboot.academic_system_with_security.mappers.StudentMapper;
import com.springboot.academic_system_with_security.mappers.UserMapper;
import com.springboot.academic_system_with_security.models.Student;
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
public class StudentServiceImp implements StudentService{

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final UserService userService;

    private final ProfessorRepository professorRepository;

    private final RoleService roleService;


    @Autowired
    public StudentServiceImp(StudentRepository studentRepository, StudentMapper studentMapper, UserService userService, ProfessorRepository professorRepository, RoleService roleService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.userService = userService;
        this.professorRepository = professorRepository;
        this.roleService = roleService;
    }

    @Override
    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(studentMapper::toStudentResponseDto);
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {
        Student student= findStudentById(id);
        return studentMapper.toStudentResponseDto(student);
    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException("Student with id: " + id + " not found!"));
    }

    @Override
    @Transactional
    public StudentWithPasswordResponseDto addStudent(StudentRequestDto studentRequestDto) {
        Student student= studentMapper.toStudent(studentRequestDto);
        validateUniqueFields(student);

        //Add inner UserEntity
        //------
        UserEntity user= new UserEntity();
        user.setUsername(studentRequestDto.getUsername());
        String password= generateRandomPassword();
        user.setPassword(password);
        student.setUser(user);

        Long roleStudentId= roleService.findRoleByRoleName(RoleName.ROLE_STUDENT).getId();

        user= userService.registerUser(user,List.of(roleStudentId));
        //------

        student.setUser(user);
        student= studentRepository.save(student);

        StudentWithPasswordResponseDto studentWithPasswordResponseDto= studentMapper.toStudentWithPasswordResponseDto(student);
        studentWithPasswordResponseDto.getUserWithPasswordResponseDto().setPassword(password);//Note:Set the decode password (the password retrieved from the DB is encoded)
        return studentWithPasswordResponseDto;
    }

    private String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(12);//Password length 12 with letters and numbers
    }

    private void validateUniqueFields(Student student) {
        validateUniqueFieldsInStudent(student);
        validateUniqueFieldsInProfessor(student);
    }

    private void validateUniqueFieldsInStudent(Student student) {
        //Validate unique fields in Student entity
        if(studentRepository.existsByDocument(student.getDocument())){
            throw new StudentDocumentExistsException("Student with Document: "+ student.getDocument()+" already exists!");
        }
        if (studentRepository.existsByEmailIgnoreCase(student.getEmail())) {
            throw new StudentEmailExistsException("Student with Email: " + student.getEmail() + " already exists!");
        }
    }

    private void validateUniqueFieldsInProfessor(Student student) {
        //Validate unique fields in Professor entity
        if(professorRepository.existsByDocument(student.getDocument())){
            throw new ProfessorDocumentExistsException("Professor with Document: "+ student.getDocument()+" already exists!");
        }
        if(professorRepository.existsByEmailIgnoreCase(student.getEmail())){
            throw new ProfessorEmailExistsException("Professor with Email: "+ student.getEmail()+" already exists!");
        }
    }

    @Override
    @Transactional
    public StudentResponseDto updateStudent(UpdateStudentRequestDto updateStudentRequestDto,Long id) {
        Student student= studentMapper.toStudent(updateStudentRequestDto);
        student.setId(id);

        Student recoveredStudent= findStudentById(id);
        validateFieldsUpdateConflict(student,recoveredStudent);

        //Update inner UserEntity
        //-----
        /*UserSummaryRequestDto userSummaryRequestDto= studentRequestDto.getUserSummaryRequestDto();
        Long userId= recoveredStudent.getUser().getId();
        UserResponseDto userResponseDto= userService.updateUser(userSummaryRequestDto,id);
        */
        UserResponseDto userResponseDto= userService.updateUser(
                updateStudentRequestDto.getUserRequestDto(),
                recoveredStudent.getUser().getId()
        );
        //-----

        BeanUtils.copyProperties(student,recoveredStudent,"user");
        return studentMapper.toStudentResponseDto(recoveredStudent);
    }

    private void validateFieldsUpdateConflict(Student student,Student recoveredStudent) {
        if(studentDocumentExistsAndBelongsToAnotherInstance(student.getDocument(),recoveredStudent)){
            throw new StudentDocumentExistsException("Student with Document: "+ student.getDocument()+" already exists!");
        }
        if(studentEmailExistsAndBelongsToAnotherInstance(student.getEmail(),recoveredStudent)){
            throw new StudentEmailExistsException("Student with Email: "+ student.getEmail()+" already exists!");
        }
        validateUniqueFieldsInProfessor(student);
    }

    private boolean studentDocumentExistsAndBelongsToAnotherInstance(String document,Student recoveredStudent) {
        return studentRepository.existsByDocument(document) && !document.equals(recoveredStudent.getDocument());
    }
    private boolean studentEmailExistsAndBelongsToAnotherInstance(String email,Student recoveredStudent) {
        return studentRepository.existsByEmailIgnoreCase(email) && !email.equalsIgnoreCase(recoveredStudent.getEmail());
    }

    @Override
    @Transactional
    public void removeStudent(Long id) {
        UserEntity user= findStudentById(id).getUser();
        userService.removeUser(user.getId());
    }

}
