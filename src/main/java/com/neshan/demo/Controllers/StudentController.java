package com.neshan.demo.Controllers;


import com.neshan.demo.Domain.Student;
import com.neshan.demo.Dto.StudentDto;
import com.neshan.demo.Exeptions.StudentNotFoundException;
import com.neshan.demo.Repositories.StudentRepository;
import com.neshan.demo.Services.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class StudentController {
    private final StudentRepository studentRepository;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private StudentService studentService;
    private ModelMapper modelMapper;
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    private StudentDto convertEntitytoDTO(Student student){
        return new StudentDto(student.getFirstName(), student.getLastName(), student.getEmail(), student.getAge());
    }
    //get all students
    @GetMapping("/students")
    List<StudentDto> all() throws IOException {
        List<StudentDto> st =  studentRepository.findAll().stream().map(this::convertEntitytoDTO).collect(Collectors.toList());
//        GsonBuilder builder = new GsonBuilder();
//        builder.setPrettyPrinting();
//        Gson gson = builder.create();
//        gson.toJson(st, new FileWriter("test.txt"));
        try (final FileOutputStream fout = new FileOutputStream("test.txt", true);
             final ObjectOutputStream out = new ObjectOutputStream(fout)) {
//            System.out.println(st.get(1).toString());
            for (int i=0; i<st.size(); i++) {
                writeIntoFile(out, st.get(i));
                out.flush();
            }
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }
    @Async
    public void writeIntoFile(ObjectOutputStream out, StudentDto s) throws IOException {
        System.out.println(Thread.currentThread().getName());
        out.writeObject(s.toString());
    }

    //get one student by id
    @Cacheable(value = "students", key = "#id", unless = "#result.age > 30")
    @GetMapping("/students/{id}")
    public StudentDto one(@PathVariable Long id) {
        LOG.info("Getting student with ID {}." , id);
        return studentRepository.findById(id).map(this::convertEntitytoDTO)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    //get students with age less than 20
    @GetMapping("/students/age")
    List<StudentDto> ageUnder20() {
        return studentRepository.findAllSortedByAgeUsingNative().stream().map(this::convertEntitytoDTO).collect(Collectors.toList());
    }

    //create new students
    @PostMapping("/students")
    StudentDto newStudent(@RequestBody StudentDto newStudent) {
        studentRepository.save(new Student(newStudent.getFirstName(), newStudent.getLastName(), newStudent.getEmail(), newStudent.getAge()));
        return  newStudent;
    }

    //edit student
    @CachePut(value = "students", key = "#id")
    @PutMapping("/students/{id}")
    public Optional<StudentDto> replaceStudent(@RequestBody StudentDto newStudent, @PathVariable Long id) {

        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(newStudent.getFirstName());
                    student.setAge(newStudent.getAge());
                    student.setEmail(newStudent.getEmail());
                    student.setLastName(newStudent.getLastName());
                    studentRepository.save(student);
                    return newStudent;
                });
//                .orElseGet(() -> {
//                    newStudent.setId(id);
//                    return studentRepository.save(newStudent);
//                });
    }

    //delete student by id
    @CacheEvict(value = "students", allEntries=true)
    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }


    /**
     *
     * @param files
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/studentswiththread", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            studentService.saveStudents(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/getUsersByThread", produces = "application/json")
    public  ResponseEntity getUsers() throws IOException {
        CompletableFuture<List<Student>> students1=studentService.findAllStudents();

        return  ResponseEntity.status(HttpStatus.OK).build();
    }

}
