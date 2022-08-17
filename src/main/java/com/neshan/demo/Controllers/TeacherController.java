package com.neshan.demo.Controllers;

import com.neshan.demo.Domain.Teacher;
import com.neshan.demo.Dto.TeacherDto;
import com.neshan.demo.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    TeacherRepository teacherRepository;

    TeacherDto convertEntityToDto(Teacher teacher){
        return new TeacherDto(teacher.getName(), teacher.getSubjects());
    }
    //get all teachers
    @GetMapping
    List<TeacherDto> getTeachers() {
        return teacherRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    //create new teacher
    @PostMapping
    TeacherDto createTeacher(@RequestBody TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherDto.getName());
        teacherRepository.save(teacher);
        return teacherDto;
    }
}