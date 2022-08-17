package com.neshan.demo.Controllers;

import com.neshan.demo.Domain.Student;
import com.neshan.demo.Domain.Subject;
import com.neshan.demo.Domain.Teacher;
import com.neshan.demo.Dto.StudentDto;
import com.neshan.demo.Dto.SubjectDto;
import com.neshan.demo.Repositories.StudentRepository;
import com.neshan.demo.Repositories.SubjectRepository;
import com.neshan.demo.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;


    private SubjectDto convertEntitytoDTO(Subject subject){
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName(subject.getName());
        subjectDto.setEnrolledStudents(subject.getEnrolledStudents());
        subjectDto.setTeacher(subject.getTeacher());
        return subjectDto;
    }
    private Subject convertDtoToEntity(SubjectDto subjectDto){
        Subject subject = new Subject();
        subject.setName(subjectDto.getName());
        subject.setTeacher(subjectDto.getTeacher());
        subject.setEnrolledStudents(subjectDto.getEnrolledStudents());
        return subject;
    }
    //get all subjects
    @GetMapping
    List<SubjectDto> getSubjects() {
        return subjectRepository.findAll().stream().map(this::convertEntitytoDTO).collect(Collectors.toList());
    }

    //create new subject
    @PostMapping
    SubjectDto createSubject(@RequestBody SubjectDto subject) {

        subjectRepository.save(convertDtoToEntity(subject));
        return subject;
    }

    //add a student to a subject
    @PutMapping("/{subjectId}/students/{studentId}")
    SubjectDto addStudentToSubject(
            @PathVariable Long subjectId,
            @PathVariable Long studentId
    ) {
        Subject subject = subjectRepository.findById(subjectId).get();
        Student student = studentRepository.findById(studentId).get();
        subject.enrolledStudents.add(student);
        subjectRepository.save(subject);
        return convertEntitytoDTO(subject);
    }

    //set a teacher for subject
    @PutMapping("/{subjectId}/teacher/{teacherId}")
    SubjectDto assignTeacherToSubject(
            @PathVariable Long subjectId,
            @PathVariable Long teacherId
    ) {
        Subject subject = subjectRepository.findById(subjectId).get();
        Teacher teacher = teacherRepository.findById(teacherId).get();
        subject.setTeacher(teacher);
        subjectRepository.save(subject);
        return convertEntitytoDTO(subject);
    }
}
