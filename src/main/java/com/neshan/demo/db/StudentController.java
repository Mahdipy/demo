package com.neshan.demo.db;

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
    Student newEmployee(@RequestBody Student newStudent) {
        return studentRepository.save(newStudent);
    }

//    @PutMapping("/employees/{id}")
//    Student replaceEmployee(@RequestBody Student newStudent, @PathVariable Long id) {
//
//        return studentRepository.findById(id)
//                .map(student -> {
//                    student.setName(newStudent.getName());
//                    student.setRole(newStudent.getRole());
//                    return studentRepository.save(employee);
//                })
//                .orElseGet(() -> {
//                    newEmployee.setId(id);
//                    return repository.save(newEmployee);
//                });
//    }
//
//    @DeleteMapping("/employees/{id}")
//    void deleteEmployee(@PathVariable Long id) {
//        studentRepository.deleteById(id);
//    }
}
