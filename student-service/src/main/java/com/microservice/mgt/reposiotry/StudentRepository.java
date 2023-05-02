package com.microservice.mgt.reposiotry;

import com.microservice.mgt.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String > {
}
