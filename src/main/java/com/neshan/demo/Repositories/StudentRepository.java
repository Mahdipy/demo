package com.neshan.demo.Repositories;

import com.neshan.demo.Domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT * FROM Student WHERE age < 20 ", nativeQuery = true)
    public List<Student> findAllSortedByAgeUsingNative();
}
