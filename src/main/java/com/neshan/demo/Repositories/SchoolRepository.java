package com.neshan.demo.Repositories;

import com.neshan.demo.Domain.School;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {
    @EntityGraph(type= EntityGraph.EntityGraphType.FETCH, value="school-graph")
    @Query(value = "select s from School s where s.schoolName =:name")
    public List<School> findSchool(String name);
}
