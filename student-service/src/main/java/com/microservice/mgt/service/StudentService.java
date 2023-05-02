package com.microservice.mgt.service;

import com.microservice.mgt.dto.NewStudentRequest;
import com.microservice.mgt.dto.StudentResponse;
import com.microservice.mgt.model.Student;
import com.microservice.mgt.reposiotry.StudentRepository;
import com.microservice.mgt.utilities.NewMemberRequest;
import com.microservice.mgt.utilities.NewMemberResponse;
import com.microservice.mgt.utilities.StudentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${student.affairs.url}")
    private String studentAffairsUrl;

    @Value("${library.service.url}")
    private String libraryServiceUrl;

    public String enrolNewStudent(NewStudentRequest request){
        Student student = Student.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .level(request.getLevel())
                .age(request.getAge())
                .build();

        log.info("student request object received");

        Student savedStudent = repository.save(student);
        log.info("new student saved to database");

        log.info("preparing to send user creation request to student affairs ");
        StudentRequest studentRequest = StudentRequest.builder()
                        .studentReg(savedStudent.getId())
                                .level(savedStudent.getLevel())
                                        .firstname(savedStudent.getFirstname())
                                                .lastname(savedStudent.getLastname())
                                                        .build();

        log.info("sending user creation request to student affairs");
        String studentAffairsResponse = webClientBuilder.build().post()
                .uri(studentAffairsUrl)
                .body(Mono.just(studentRequest), StudentRequest.class)
                .retrieve()
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, response -> response.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(String.class)
                .block();

        log.info("response from student affairs: {}", studentAffairsResponse);

        log.info("creating library membership request");
        NewMemberRequest libraryMemberRequest = NewMemberRequest.builder()
                        .registrationId(savedStudent.getId())
                                .level(savedStudent.getLevel())
                                        .build();

        log.info("sending member creation request to library");
        NewMemberResponse libraryResponse = webClientBuilder.build().post()
                        .uri(libraryServiceUrl)
                .body(Mono.just(libraryMemberRequest), NewMemberRequest.class)
                                .retrieve()
                                        .bodyToMono(NewMemberResponse.class)
                                                .block();

        log.info("response from library: {}", libraryResponse);



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
