package com.microservice.mgt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewStudentRequest {
    private String firstname, lastname, level;
    private int age;
}
