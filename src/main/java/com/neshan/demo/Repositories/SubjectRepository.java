package com.neshan.demo.Repositories;

import com.neshan.demo.Domain.Student;
import com.neshan.demo.Domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
