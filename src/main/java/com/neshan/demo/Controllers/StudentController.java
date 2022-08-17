package com.neshan.demo.Controllers;

import com.neshan.demo.Domain.Student;
import com.neshan.demo.Dto.StudentDto;
import com.neshan.demo.Exeptions.StudentNotFoundException;
import com.neshan.demo.Repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StudentController {
    private final StudentRepository studentRepository;

    private ModelMapper modelMapper;
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    private StudentDto convertEntitytoDTO(Student student){
        return new StudentDto(student.getFirstName(), student.getLastName(), student.getEmail(), student.getAge());
    }
    //get all students
    @GetMapping("/students")
    List<StudentDto> all() {
        return studentRepository.findAll().stream().map(this::convertEntitytoDTO).collect(Collectors.toList());
    }

    //get one student by id
    @GetMapping("/students/{id}")
    StudentDto one(@PathVariable Long id) {

        return studentRepository.findById(id).map(this::convertEntitytoDTO)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    //get students with age less than 20
    @GetMapping("/students/age")
    List<StudentDto> ageUnder20() {
        return studentRepository.findAllSortedByAgeUsingNative().stream().map(this::convertEntitytoDTO).collect(Collectors.toList());
    }

    //create new students
    @PostMapping("/students")
    StudentDto newStudent(@RequestBody StudentDto newStudent) {
        studentRepository.save(new Student(newStudent.getFirstName(), newStudent.getLastName(), newStudent.getEmail(), newStudent.getAge()));
        return  newStudent;
    }

    //edit student
    @PutMapping("/students/{id}")
    Optional<StudentDto> replaceStudent(@RequestBody StudentDto newStudent, @PathVariable Long id) {

        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(newStudent.getFirstName());
                    student.setAge(newStudent.getAge());
                    student.setEmail(newStudent.getEmail());
                    student.setLastName(newStudent.getLastName());
                    studentRepository.save(student);
                    return newStudent;
                });
//                .orElseGet(() -> {
//                    newStudent.setId(id);
//                    return studentRepository.save(newStudent);
//                });
    }

    //delete student by id
    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}
