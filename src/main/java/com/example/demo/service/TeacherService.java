package com.example.demo.service;

import com.example.demo.dto.CreateTeacherRequest;
import com.example.demo.dto.TeacherDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Teacher;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TeacherDTO createTeacher(CreateTeacherRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(UserRole.TEACHER);
        user.setActive(true);
        user = userRepository.save(user);

        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacher.setEmployeeNumber(request.getEmployeeNumber());
        teacher.setDepartment(request.getDepartment());
        teacher.setSpecialization(request.getSpecialization());

        teacher = teacherRepository.save(teacher);
        return convertToDTO(teacher);
    }

    public TeacherDTO getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return convertToDTO(teacher);
    }

    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TeacherDTO updateTeacher(Long id, CreateTeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        User user = teacher.getUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);

        teacher.setDepartment(request.getDepartment());
        teacher.setSpecialization(request.getSpecialization());

        teacher = teacherRepository.save(teacher);
        return convertToDTO(teacher);
    }

    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        User user = teacher.getUser();
        user.setActive(false);
        userRepository.save(user);
    }

    private TeacherDTO convertToDTO(Teacher teacher) {
        UserDTO userDTO = new UserDTO(teacher.getUser().getId(), teacher.getUser().getEmail(),
                teacher.getUser().getFirstName(), teacher.getUser().getLastName(),
                teacher.getUser().getRole().name(), teacher.getUser().isActive());

        return new TeacherDTO(teacher.getId(), userDTO, teacher.getEmployeeNumber(),
                teacher.getDepartment(), teacher.getSpecialization());
    }
}
