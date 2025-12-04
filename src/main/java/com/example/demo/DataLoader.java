package com.example.demo;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Create Admin User
        User admin = new User();
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRole(UserRole.ADMIN);
        admin.setActive(true);
        userRepository.save(admin);

        // Create Teacher Users
        User teacher1 = new User();
        teacher1.setEmail("teacher1@example.com");
        teacher1.setPassword(passwordEncoder.encode("teacher123"));
        teacher1.setFirstName("Ali");
        teacher1.setLastName("Yilmaz");
        teacher1.setRole(UserRole.TEACHER);
        teacher1.setActive(true);
        teacher1 = userRepository.save(teacher1);

        User teacher2 = new User();
        teacher2.setEmail("teacher2@example.com");
        teacher2.setPassword(passwordEncoder.encode("teacher123"));
        teacher2.setFirstName("Ayşe");
        teacher2.setLastName("Demir");
        teacher2.setRole(UserRole.TEACHER);
        teacher2.setActive(true);
        teacher2 = userRepository.save(teacher2);

        // Create Teacher Records
        Teacher t1 = new Teacher();
        t1.setUser(teacher1);
        t1.setEmployeeNumber("EMP001");
        t1.setDepartment("Computer Science");
        t1.setSpecialization("Data Science");
        teacherRepository.save(t1);

        Teacher t2 = new Teacher();
        t2.setUser(teacher2);
        t2.setEmployeeNumber("EMP002");
        t2.setDepartment("Mathematics");
        t2.setSpecialization("Calculus");
        teacherRepository.save(t2);

        // Create Student Users
        User student1 = new User();
        student1.setEmail("student1@example.com");
        student1.setPassword(passwordEncoder.encode("student123"));
        student1.setFirstName("Mehmet");
        student1.setLastName("Kaya");
        student1.setRole(UserRole.STUDENT);
        student1.setActive(true);
        student1 = userRepository.save(student1);

        User student2 = new User();
        student2.setEmail("student2@example.com");
        student2.setPassword(passwordEncoder.encode("student123"));
        student2.setFirstName("Fatma");
        student2.setLastName("Şahin");
        student2.setRole(UserRole.STUDENT);
        student2.setActive(true);
        student2 = userRepository.save(student2);

        // Create Student Records
        Student s1 = new Student();
        s1.setUser(student1);
        s1.setStudentNumber("STU001");
        s1.setDepartment("Computer Science");
        s1.setSemester(3);
        studentRepository.save(s1);

        Student s2 = new Student();
        s2.setUser(student2);
        s2.setStudentNumber("STU002");
        s2.setDepartment("Mathematics");
        s2.setSemester(2);
        studentRepository.save(s2);

        // Create Courses
        Course c1 = new Course();
        c1.setCourseCode("CS101");
        c1.setCourseName("Introduction to Programming");
        c1.setDescription("Learn basic programming concepts");
        c1.setCredit(3);
        c1.setSemester(1);
        c1.setTeacher(t1);
        courseRepository.save(c1);

        Course c2 = new Course();
        c2.setCourseCode("CS201");
        c2.setCourseName("Data Structures");
        c2.setDescription("Learn advanced data structures");
        c2.setCredit(4);
        c2.setSemester(2);
        c2.setTeacher(t1);
        courseRepository.save(c2);

        Course c3 = new Course();
        c3.setCourseCode("MATH101");
        c3.setCourseName("Calculus I");
        c3.setDescription("Introduction to Calculus");
        c3.setCredit(4);
        c3.setSemester(1);
        c3.setTeacher(t2);
        courseRepository.save(c3);

        // Create Enrollments
        Enrollment e1 = new Enrollment();
        e1.setStudent(s1);
        e1.setCourse(c1);
        e1.setEnrollmentDate(LocalDateTime.now());
        e1.setStatus("ENROLLED");
        enrollmentRepository.save(e1);

        Enrollment e2 = new Enrollment();
        e2.setStudent(s1);
        e2.setCourse(c2);
        e2.setEnrollmentDate(LocalDateTime.now());
        e2.setGrade(85.0);
        e2.setStatus("COMPLETED");
        enrollmentRepository.save(e2);

        Enrollment e3 = new Enrollment();
        e3.setStudent(s2);
        e3.setCourse(c3);
        e3.setEnrollmentDate(LocalDateTime.now());
        e3.setStatus("ENROLLED");
        enrollmentRepository.save(e3);
    }
}
