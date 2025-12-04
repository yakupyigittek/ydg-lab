package com.example.demo.service;

import com.example.demo.dto.CreateStudentRequest;
import com.example.demo.dto.StudentDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public StudentDTO createStudent(CreateStudentRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(UserRole.STUDENT);
        user.setActive(true);
        user = userRepository.save(user);

        Student student = new Student();
        student.setUser(user);
        student.setStudentNumber(request.getStudentNumber());
        student.setDepartment(request.getDepartment());
        student.setSemester(request.getSemester());

        student = studentRepository.save(student);
        return convertToDTO(student);
    }

    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return convertToDTO(student);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO updateStudent(Long id, CreateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        User user = student.getUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);

        student.setDepartment(request.getDepartment());
        student.setSemester(request.getSemester());

        student = studentRepository.save(student);
        return convertToDTO(student);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        User user = student.getUser();
        user.setActive(false);
        userRepository.save(user);
    }

    private StudentDTO convertToDTO(Student student) {
        UserDTO userDTO = new UserDTO(student.getUser().getId(), student.getUser().getEmail(),
                student.getUser().getFirstName(), student.getUser().getLastName(),
                student.getUser().getRole().name(), student.getUser().isActive());

        return new StudentDTO(student.getId(), userDTO, student.getStudentNumber(),
                student.getDepartment(), student.getSemester());
    }
}
