package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.CourseRequestDto;
import com.springboot.academic_system_with_security.dtos.CourseResponseDto;
import com.springboot.academic_system_with_security.exceptions.CourseCodeExistsException;
import com.springboot.academic_system_with_security.exceptions.CourseMaxStudentsLessThanCurrentStudents;
import com.springboot.academic_system_with_security.exceptions.CourseNotFoundException;
import com.springboot.academic_system_with_security.mappers.CourseMapper;
import com.springboot.academic_system_with_security.models.Course;
import com.springboot.academic_system_with_security.models.Professor;
import com.springboot.academic_system_with_security.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImp implements CourseService{

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final ProfessorService professorService;

    @Autowired
    public CourseServiceImp(CourseRepository courseRepository, CourseMapper courseMapper, ProfessorService professorService) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.professorService = professorService;
    }

    @Override
    public Page<CourseResponseDto> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseMapper::toCourseResponseDto);
    }

    @Override
    public CourseResponseDto getCourseById(Long id) {
        Course course= findCourseById(id);
        return courseMapper.toCourseResponseDto(course);
    }

    public Course findCourseById(Long id){
        return courseRepository.findById(id).orElseThrow(() ->new CourseNotFoundException("Course with id: " + id + " not found!"));
    }

    @Override
    public Page<CourseResponseDto> getCoursesByProfessorId(Long professorId,Pageable pageable) {
        Professor professor= professorService.findProfessorById(professorId);
        return courseRepository.findAllByProfessor(professor,pageable).map(courseMapper::toCourseResponseDto);
    }

    @Override
    @Transactional
    public CourseResponseDto addCourse(CourseRequestDto courseRequestDto,Long professorId) {
        Course course= courseMapper.toCourse(courseRequestDto);
        course.setCurrentStudents(0);
        validateUniqueFields(course);

        Professor professor= professorService.findProfessorById(professorId);

        course.setProfessor(professor);
        return courseMapper.toCourseResponseDto(courseRepository.save(course));
    }

    private void validateUniqueFields(Course course) {
        if(courseRepository.existsByCourseCodeIgnoreCase(course.getCourseCode())){
            throw new CourseCodeExistsException("Course Code: "+ course.getCourseCode()+" already exists!");
        }
    }

    @Override
    @Transactional
    public CourseResponseDto updateCourse(CourseRequestDto courseRequestDto,Long id) {
        Course course= courseMapper.toCourse(courseRequestDto);
        course.setId(id);

        Course recoveredCourse= findCourseById(id);
        validateFieldsUpdateConflict(course,recoveredCourse);

        BeanUtils.copyProperties(course,recoveredCourse,"currentStudents","professor","courseStudentList");//Note:Ignore relationships properties!
        return courseMapper.toCourseResponseDto(recoveredCourse);
    }

    private void validateFieldsUpdateConflict(Course course,Course recoveredCourse) {
        if(courseCodeExistsAndBelongsToAnotherInstance(course.getCourseCode(),recoveredCourse)){
            throw new CourseCodeExistsException("Course Code: "+ course.getCourseCode()+" already exists!");
        }
        if(course.getMaxStudents()<recoveredCourse.getCurrentStudents()){
            throw new CourseMaxStudentsLessThanCurrentStudents("Max Students:"+course.getMaxStudents()+", is less than Current Students: "+recoveredCourse.getCurrentStudents());
        }
    }

    private boolean courseCodeExistsAndBelongsToAnotherInstance(String courseCode,Course recoveredCourse) {
        return courseRepository.existsByCourseCodeIgnoreCase(courseCode) && !courseCode.equalsIgnoreCase(recoveredCourse.getCourseCode());
    }

    @Override
    @Transactional
    public void removeCourse(Long id) {
        Course course= findCourseById(id);
        courseRepository.delete(course);
    }

}
