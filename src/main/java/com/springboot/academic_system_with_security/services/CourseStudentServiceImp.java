package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.*;
import com.springboot.academic_system_with_security.exceptions.CourseStudentExistsException;
import com.springboot.academic_system_with_security.exceptions.CourseStudentNotFoundException;
import com.springboot.academic_system_with_security.mappers.CourseMapper;
import com.springboot.academic_system_with_security.mappers.CourseStudentMapper;
import com.springboot.academic_system_with_security.mappers.StudentMapper;
import com.springboot.academic_system_with_security.models.Course;
import com.springboot.academic_system_with_security.models.CourseStudent;
import com.springboot.academic_system_with_security.models.CourseStudentId;
import com.springboot.academic_system_with_security.models.Student;
import com.springboot.academic_system_with_security.repository.CourseStudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CourseStudentServiceImp implements CourseStudentService{

    private final CourseStudentRepository courseStudentRepository;

    private final StudentService studentService;

    private final CourseService courseService;

    private final CourseStudentMapper courseStudentMapper;

    private final CourseMapper courseMapper;

    private final StudentMapper studentMapper;

    @Autowired
    public CourseStudentServiceImp(CourseStudentRepository courseStudentRepository, StudentService studentService, CourseService courseService, CourseStudentMapper courseStudentMapper, CourseMapper courseMapper, StudentMapper studentMapper) {
        this.courseStudentRepository = courseStudentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
        this.courseStudentMapper = courseStudentMapper;
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    public CourseStudentResponseDto getCourseStudentById(CourseStudentRequestDto courseStudentRequestDto) {
        Long courseId= courseStudentRequestDto.getCourseId();
        Long studentId= courseStudentRequestDto.getStudentId();
        CourseStudent courseStudent= findCourseStudentById(courseId,studentId);
        return courseStudentMapper.toCourseStudentResponseDto(courseStudent);
    }

    private CourseStudent findCourseStudentById(Long courseId,Long studentId){
        CourseStudentId courseStudentId= new CourseStudentId(courseId,studentId);
        return findCourseStudentById(courseStudentId);
    }

    private CourseStudent findCourseStudentById(CourseStudentId courseStudentId){
        return courseStudentRepository.findById(courseStudentId)
                .orElseThrow(()->new CourseStudentNotFoundException(String.format("Course x Student: (Course id: %d, Student id: %d) not found!",courseStudentId.getCourseId(),courseStudentId.getStudentId())));
    }

    @Override
    public List<CourseResponseDto> getCoursesByStudentId(Long studentId) {
        Student student= studentService.findStudentById(studentId);
        List<CourseStudent> courseStudentList= courseStudentRepository.findByStudent(student);

        return courseStudentList.stream()
                .map(CourseStudent::getCourse)
                .map(courseMapper::toCourseResponseDto)
                .collect(toList());
    }

    @Override
    public List<CourseStudentWithCourseResponseDto> getCourseStudentWithCourseListByStudentId(Long studentId) {
        Student student= studentService.findStudentById(studentId);
        List<CourseStudent> courseStudentList= courseStudentRepository.findByStudent(student);

        return courseStudentList.stream()
                .map(courseStudentMapper::toCourseStudentWithCourseResponseDto)
                .collect(toList());
    }

    @Override
    public List<StudentResponseDto> getStudentsByCourseId(Long courseId) {
        Course course= courseService.findCourseById(courseId);
        List<CourseStudent> courseStudentList= courseStudentRepository.findByCourse(course);

        return courseStudentList.stream()
                .map(CourseStudent::getStudent)
                .map(studentMapper::toStudentResponseDto)
                .collect(toList());
    }

    @Override
    public List<StudentResponseDto> getStudentsByCourseIdAndContainingName(Long courseId,String studentName) {
        Course course= courseService.findCourseById(courseId);
        List<CourseStudent> courseStudentList= courseStudentRepository.findByCourse(course);

        Predicate<Student> containsStudentName= student-> student.getFirstName().toLowerCase().contains(studentName.toLowerCase());

        return courseStudentList.stream()
                .map(CourseStudent::getStudent)
                .filter(containsStudentName)
                .map(studentMapper::toStudentResponseDto)
                .collect(toList());
    }

    @Override
    public List<CourseStudentWithStudentResponseDto> getCourseStudentWithStudentListByCourseId(Long courseId) {
        Course course= courseService.findCourseById(courseId);
        List<CourseStudent> courseStudentList= courseStudentRepository.findByCourse(course);

        return courseStudentList.stream()
                .map(courseStudentMapper::toCourseStudentWithStudentResponseDto)
                .collect(toList());
    }

    @Override
    @Transactional
    public List<CourseStudentResponseDto> addCourseStudent(AddCourseStudentRequestDto addCourseStudentRequestDto) {
        Long studentId= addCourseStudentRequestDto.getStudentId();
        List<Long> courseIds= addCourseStudentRequestDto.getCourseIds();
        List<CourseStudentResponseDto> courseStudentResponseDtoList= courseIds.stream()
                .map(courseId->addCourseStudent(studentId,courseId))
                .map(courseStudentMapper::toCourseStudentResponseDto)
                .toList();
        //Note: For returning a Page<>.
        //Page<CourseStudentResponseDto> CourseStudentResponseDtoPage= new PageImpl<>(courseStudentResponseDtoList,pageable,courseStudentResponseDtoList.size());
        return courseStudentResponseDtoList;
    }

    private CourseStudent addCourseStudent(Long studentId,Long courseId){
        Student student= studentService.findStudentById(studentId);
        Course course= courseService.findCourseById(courseId);

        validateUniqueId(studentId,courseId);

        course.enrollStudent();

        CourseStudent courseStudent= new CourseStudent(course,student,0D);//Or: CourseStudent courseStudent= new CourseStudent(new CourseStudentId(),course,student,0D);
        return courseStudentRepository.save(courseStudent);
    }

    private void validateUniqueId(Long studentId,Long courseId) {
        CourseStudentId courseStudentId= new CourseStudentId(courseId,studentId);
        validateUniqueId(courseStudentId);
    }

    private void validateUniqueId(CourseStudentId courseStudentId){
        if(courseStudentRepository.existsById(courseStudentId)){
            String exceptionMessage= String.format("Course x Student: (Course id: %d, Student id: %d) already exist!",courseStudentId.getCourseId(),courseStudentId.getStudentId());
            throw new CourseStudentExistsException(exceptionMessage);
        }
    }

    @Override
    @Transactional
    public CourseStudentResponseDto setCourseStudentScore(CourseStudentScoreRequestDto courseStudentScoreRequestDto) {
        Long courseId= courseStudentScoreRequestDto.getCourseId();
        Long studentId= courseStudentScoreRequestDto.getStudentId();
        Double score= courseStudentScoreRequestDto.getScore();

        CourseStudent courseStudent= findCourseStudentById(courseId,studentId);
        courseStudent.setScore(score);

        return courseStudentMapper.toCourseStudentResponseDto(courseStudent);
    }

    @Override
    @Transactional
    public void removeCourseStudent(CourseStudentRequestDto courseStudentRequestDto) {
        Long courseId= courseStudentRequestDto.getCourseId();
        Long studentId= courseStudentRequestDto.getStudentId();
        CourseStudent courseStudent= findCourseStudentById(courseId,studentId);

        courseStudent.getCourse().unenrollStudent();
        courseStudentRepository.delete(courseStudent);
    }

}

