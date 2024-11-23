package com.springboot.academic_system_with_security.repository;

import com.springboot.academic_system_with_security.models.Course;
import com.springboot.academic_system_with_security.models.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    boolean existsByCourseCodeIgnoreCase(String courseCode);

    Page<Course> findAllByProfessor(Professor professor,Pageable pageable);
}
