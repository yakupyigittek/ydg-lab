package com.example.demo.service;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.CreateCourseRequest;
import com.example.demo.entity.Course;
import com.example.demo.entity.Teacher;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public CourseDTO createCourse(CreateCourseRequest request) {
        if (courseRepository.findByCourseCode(request.getCourseCode()).isPresent()) {
            throw new RuntimeException("Course code already exists");
        }

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = new Course();
        course.setCourseCode(request.getCourseCode());
        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setCredit(request.getCredit());
        course.setSemester(request.getSemester());
        course.setTeacher(teacher);

        course = courseRepository.save(course);
        return convertToDTO(course);
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return convertToDTO(course);
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getCoursesByTeacherId(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getCoursesBySemester(Integer semester) {
        return courseRepository.findBySemester(semester).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO updateCourse(Long id, CreateCourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setCredit(request.getCredit());
        course.setSemester(request.getSemester());

        if (!course.getTeacher().getId().equals(request.getTeacherId())) {
            Teacher teacher = teacherRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            course.setTeacher(teacher);
        }

        course = courseRepository.save(course);
        return convertToDTO(course);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        courseRepository.delete(course);
    }

    private CourseDTO convertToDTO(Course course) {
        return new CourseDTO(course.getId(), course.getCourseCode(), course.getCourseName(),
                course.getDescription(), course.getCredit(), course.getSemester(),
                course.getTeacher().getId(),
                course.getTeacher().getUser().getFirstName() + " " + course.getTeacher().getUser().getLastName());
    }
}
