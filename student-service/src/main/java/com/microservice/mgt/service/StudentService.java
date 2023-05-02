package com.microservice.mgt.service;

import com.microservice.mgt.dto.NewStudentRequest;
import com.microservice.mgt.dto.StudentResponse;
import com.microservice.mgt.model.Student;
import com.microservice.mgt.reposiotry.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public String enrolNewStudent(NewStudentRequest request){
        Student student = Student.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .level(request.getLevel())
                .age(request.getAge())
                .build();

        repository.save(student);

        return "student enrolled successfully";
    }

    public List<StudentResponse> getAllStudents(){
        List<Student> students = repository.findAll();

        return students.stream().map(this::mapToStudentResponse).collect(Collectors.toList());
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .firstname(student.getFirstname())
                .lastname(student.getLastname())
                .level(student.getLevel())
                .age(student.getAge())
                .id(student.getId())
                .build();
    }

    public StudentResponse getStudentById(String id){
        return mapToStudentResponse(repository.findById(id).get());
    }
}
