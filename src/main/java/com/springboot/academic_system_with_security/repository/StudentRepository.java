package com.springboot.academic_system_with_security.repository;

import com.springboot.academic_system_with_security.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    boolean existsByDocument(String document);

    boolean existsByEmailIgnoreCase(String email);

}
