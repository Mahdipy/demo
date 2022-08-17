package com.neshan.demo.Domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.util.List;

@NamedEntityGraphs(
        value = {
                @NamedEntityGraph(
                        name = "school-graph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "students")
                        }
                )
        }
)
@Entity
@Table(name = "school")
public class School {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "school_name")
    private String schoolName;

    public Integer getId() {
        return id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @OneToMany(mappedBy = "school", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Student> students;
}
