package com.microservice.mgt.controller;

import com.microservice.mgt.dto.NewStudentRequest;
import com.microservice.mgt.dto.StudentResponse;
import com.microservice.mgt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class CoreController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/enrol")
    @ResponseStatus(HttpStatus.CREATED)
    public String enrolNewStudent(@RequestBody NewStudentRequest request) {
        return studentService.enrolNewStudent(request);
    }

    @GetMapping("/getall")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/getbyid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponse enrolNewStudent(@PathVariable(name = "id") String id) {
        return studentService.getStudentById(id);
    }
}
