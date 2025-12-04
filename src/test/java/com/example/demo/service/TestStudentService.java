package com.example.demo.service;

import com.example.demo.dto.CreateStudentRequest;
import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestStudentService {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentService studentService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void testCreateStudent(){
        CreateStudentRequest request = new CreateStudentRequest();
        request.setEmail("student@example.com");
        request.setPassword("password");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setStudentNumber("12345");
        request.setDepartment("Computer Science");
        request.setSemester(1);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(org.mockito.ArgumentMatchers.any(CharSequence.class))).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(2L);
        savedUser.setEmail(request.getEmail());
        savedUser.setFirstName(request.getFirstName());
        savedUser.setLastName(request.getLastName());
        savedUser.setRole(UserRole.STUDENT);
        savedUser.setActive(true);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setUser(savedUser);
        savedStudent.setStudentNumber(request.getStudentNumber());
        savedStudent.setDepartment(request.getDepartment());
        savedStudent.setSemester(request.getSemester());
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentDTO result = studentService.createStudent(request);

        assertNotNull(result);
        assertNotEquals(savedStudent.getId(), result.getId());
        assertEquals(savedUser.getId(), result.getUser().getId());
        assertEquals(request.getStudentNumber(), result.getStudentNumber());
        assertEquals(request.getDepartment(), result.getDepartment());

    }



}