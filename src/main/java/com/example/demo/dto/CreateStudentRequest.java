package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String studentNumber;
    private String department;
    private Integer semester;
}
