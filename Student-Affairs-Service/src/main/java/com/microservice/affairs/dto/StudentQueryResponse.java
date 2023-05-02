package com.microservice.affairs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentQueryResponse {
    private String id;
    private String studentReg, firstname, lastname, level;
}
