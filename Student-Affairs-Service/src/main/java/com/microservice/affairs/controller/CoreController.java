package com.microservice.affairs.controller;

import com.microservice.affairs.model.Student;
import com.microservice.affairs.service.StudentService;
import com.microservice.affairs.dto.StudentQueryResponse;
import com.microservice.affairs.dto.StudentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affairs")
@Slf4j
public class CoreController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/newstudent")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerNewStudent(@RequestBody StudentRequest request) {
        log.info("request object received: {}", request);
        return studentService.addStudent(request);
    }

    @PostMapping("/allstudents")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentQueryResponse> allStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/studentbyid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentQueryResponse registerNewStudent(@PathVariable(name = "id") String id) {
        return studentService.getStudentById(id);
    }
}
