package com.neshan.demo.Exeptions;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(Long id) {
        super("Could not find Student with id = " + id);
    }
}
