package com.neshan.demo.Services;


import com.neshan.demo.Domain.Student;
import com.neshan.demo.Repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    Object target;
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    @Async
    public CompletableFuture<List<Student>> saveStudents(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<Student> students = parseCSVFile(file);
        logger.info("saving list of students of size {}", students.size(), "" + Thread.currentThread().getName());
        students = repository.saveAll(students);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return CompletableFuture.completedFuture(students);
    }

    @Async
    public CompletableFuture<List<Student>> findAllStudents(){
        logger.info("get list of user by "+Thread.currentThread().getName());
        List<Student> students=repository.findAll();
        return CompletableFuture.completedFuture(students);
    }

    private List<Student> parseCSVFile(final MultipartFile file) throws Exception {
        final List<Student> students = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final Student student = new Student();
                    student.setFirstName(data[0]);
                    student.setLastName(data[1]);
                    student.setEmail(data[2]);
                    student.setAge(Integer.parseInt(data[3]));
                    students.add(student);
                }
                return students;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
}
