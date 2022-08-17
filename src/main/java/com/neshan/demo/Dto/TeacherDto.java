package com.neshan.demo.Dto;

import com.neshan.demo.Domain.Subject;

import java.util.Set;

public class TeacherDto {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Set<Subject> subjects;

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public TeacherDto(String name, Set<Subject> subjects) {
        this.name = name;
        this.subjects = subjects;
    }
}
