package com.microservice.affairs.controller;

import com.microservice.affairs.model.Student;
import com.microservice.affairs.service.StudentService;
import com.microservice.affairs.dto.StudentQueryResponse;
import com.microservice.affairs.dto.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/affairs")
public class CoreController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/newstudent")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerNewStudent(@RequestBody StudentRequest request) {
        return studentService.addStudent(request);
    }

    @PostMapping("/allstudents")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentQueryResponse> allStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/studentbyid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentQueryResponse registerNewStudent(@PathVariable(name = "id") int id) {
        return studentService.getStudentById(id);
    }
}
