package com.neshan.demo.db;

public class StudentNotFoundException extends RuntimeException{
    StudentNotFoundException(Long id) {
        super("Could not find Student with id = " + id);
    }
}
