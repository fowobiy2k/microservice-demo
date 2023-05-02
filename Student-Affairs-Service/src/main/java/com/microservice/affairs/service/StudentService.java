package com.microservice.affairs.service;

import com.microservice.affairs.dto.StudentRequest;
import com.microservice.affairs.dto.StudentQueryResponse;
import com.microservice.affairs.model.Student;
import com.microservice.affairs.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public String addStudent(StudentRequest request) {
        Student member = Student.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .level(request.getLevel())
                .studentReg(request.getStudentReg())
                .build();

        studentRepository.save(member);

        return "new member registered successfully";
    }

    public List<StudentQueryResponse> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::mapToStudentResponse).collect(Collectors.toList());
    }

    public StudentQueryResponse getStudentById(int id) {
        return mapToStudentResponse(studentRepository.findById(id).get());
    }

    private StudentQueryResponse mapToStudentResponse(Student student) {
        return StudentQueryResponse.builder()
                .id(student.getId())
                .studentReg(student.getStudentReg())
                .firstname(student.getFirstname())
                .lastname(student.getLastname())
                .level(student.getLevel())
                .build();
    }
}
