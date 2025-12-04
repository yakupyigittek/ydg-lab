package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeacherRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String employeeNumber;
    private String department;
    private String specialization;
}
