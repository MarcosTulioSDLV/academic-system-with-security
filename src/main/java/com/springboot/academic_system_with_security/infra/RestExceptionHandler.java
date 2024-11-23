package com.springboot.academic_system_with_security.infra;

import com.springboot.academic_system_with_security.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    //For Role class

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException e){
        return handleCustomerException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNameNotFoundException.class)
    public ResponseEntity<Object> handleRoleNameNotFoundException(RoleNameNotFoundException e){
        return handleCustomerException(e,HttpStatus.NOT_FOUND);
    }

    /*@ExceptionHandler(RoleNameExistsException.class)
    public ResponseEntity<Object> handleRoleNameExistsException(RoleNameExistsException e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }*/

    //Note: This exception is thrown from UserServiceImp Class
    @ExceptionHandler(InvalidRoleIdsException.class)
    public ResponseEntity<Object> handleInvalidRoleIdsException(InvalidRoleIdsException e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }

    //-----
    //For User class

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e){
        return handleCustomerException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<Object> handleUsernameExistsException(UsernameExistsException e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }
    //-----
    //For Student class

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Object> handleStudentNotFoundException(StudentNotFoundException e){
        return handleCustomerException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentDocumentExistsException.class)
    public ResponseEntity<Object> handleStudentDocumentExistsException(StudentDocumentExistsException e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentEmailExistsException.class)
    public ResponseEntity<Object> handleStudentEmailExistsException(StudentEmailExistsException e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }

    //-----
    //For Professor class

    @ExceptionHandler(ProfessorNotFoundException.class)
    public ResponseEntity<Object> handleProfessorNotFoundException(ProfessorNotFoundException e){
        return handleCustomerException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProfessorDocumentExistsException.class)
    public ResponseEntity<Object> handleProfessorDocumentExistsException(ProfessorDocumentExistsException e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfessorPhoneNumberExistsException.class)
    public ResponseEntity<Object> handleProfessorPhoneNumberExistsException(ProfessorPhoneNumberExistsException e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfessorEmailExistsException.class)
    public ResponseEntity<Object> handleProfessorEmailExistsException(ProfessorEmailExistsException e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }

    //-----
    //For Course class

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFoundException(CourseNotFoundException e){
        return handleCustomerException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseCodeExistsException.class)
    public ResponseEntity<Object> handleCourseCodeExistsException(CourseCodeExistsException e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CourseMaxStudentsLessThanCurrentStudents.class)
    public ResponseEntity<Object> handleCourseMaxStudentsLessThanCurrentStudents(CourseMaxStudentsLessThanCurrentStudents e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CourseMaxStudentsFullException.class)
    public ResponseEntity<Object> handleCourseMaxStudentsFullException(CourseMaxStudentsFullException e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }
    //-----
    //For CourseStudent class

    @ExceptionHandler(CourseStudentNotFoundException.class)
    public ResponseEntity<Object> handleCourseStudentNotFoundException(CourseStudentNotFoundException e){
        return handleCustomerException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseStudentExistsException.class)
    public ResponseEntity<Object> handleCourseStudentExistsException(CourseStudentExistsException e){
        return handleCustomerException(e,HttpStatus.BAD_REQUEST);
    }

    //-----
    private ResponseEntity<Object> handleCustomerException(RuntimeException e,HttpStatus httpStatus){
        Map<String,Object> responseMessage= getResponseMessage(e.getMessage(),httpStatus);
        return new ResponseEntity<>(responseMessage,httpStatus);
    }

    private Map<String,Object> getResponseMessage(String message,HttpStatus httpStatus){
        Map<String,Object> responseMessage= new LinkedHashMap<>();
        responseMessage.put("message",message);
        responseMessage.put("status",httpStatus.value());
        return responseMessage;
    }

}
