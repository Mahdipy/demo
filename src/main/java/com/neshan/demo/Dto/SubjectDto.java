package com.neshan.demo.Dto;

import com.neshan.demo.Domain.Student;
import com.neshan.demo.Domain.Teacher;

import java.util.HashSet;
import java.util.Set;

public class SubjectDto {
    private String name;
    public Set<Student> enrolledStudents = new HashSet<>();
    private Teacher teacher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Set<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
