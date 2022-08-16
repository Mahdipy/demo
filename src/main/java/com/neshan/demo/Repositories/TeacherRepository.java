package com.neshan.demo.Repositories;

import com.neshan.demo.Domain.Student;
import com.neshan.demo.Domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
