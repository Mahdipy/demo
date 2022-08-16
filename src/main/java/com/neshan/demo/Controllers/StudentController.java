package com.neshan.demo.Controllers;

import com.neshan.demo.Domain.Student;
import com.neshan.demo.Domain.StudentDto;
import com.neshan.demo.Exeptions.StudentNotFoundException;
import com.neshan.demo.Repositories.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    @GetMapping("/students")
    List<Student> all() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    Student one(@PathVariable Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @PostMapping("/students")
    Student newStudent(@RequestBody StudentDto newStudent) {
        return studentRepository.save(new Student(newStudent.getFirstName(), newStudent.getLastName(), newStudent.getEmail(), newStudent.getAge()));
    }

    @PutMapping("/students/{id}")
    Student replaceStudent(@RequestBody Student newStudent, @PathVariable Long id) {

        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(newStudent.getFirstName());
                    student.setAge(newStudent.getAge());
                    student.setEmail(newStudent.getEmail());
                    student.setLastName(newStudent.getLastName());
                    return studentRepository.save(student);
                })
                .orElseGet(() -> {
                    newStudent.setId(id);
                    return studentRepository.save(newStudent);
                });
    }
//
    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}
