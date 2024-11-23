package com.springboot.academic_system_with_security.repository;

import com.springboot.academic_system_with_security.models.Course;
import com.springboot.academic_system_with_security.models.CourseStudent;
import com.springboot.academic_system_with_security.models.CourseStudentId;
import com.springboot.academic_system_with_security.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent,CourseStudentId> {

    List<CourseStudent> findByStudent(Student student);

    List<CourseStudent> findByCourse(Course course);

}
