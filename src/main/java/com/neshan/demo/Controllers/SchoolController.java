package com.neshan.demo.Controllers;

import com.neshan.demo.Domain.School;
import com.neshan.demo.Repositories.SchoolRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class SchoolController {

    private final SchoolRepository schoolRepository;

    public SchoolController(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public SchoolRepository getSchoolRepository() {
        return schoolRepository;
    }

    @GetMapping("/schools")
    List<School> all() {
        return schoolRepository.findAll();
    }

    @PostMapping("/schools")
    School newSchool(@RequestBody School newSchool) {
        return schoolRepository.save(newSchool);
    }
}
