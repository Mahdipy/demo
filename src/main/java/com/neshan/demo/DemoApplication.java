package com.neshan.demo;

import com.neshan.demo.Domain.Student;
import com.neshan.demo.Repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository){
		return args -> {
			Student student = new Student(
					"ali",
					"ghasemi",
					"ali@gmail.com",
					14
			);
			studentRepository.save(student);
			Student student2 = new Student(
					"mahdi",
					"py",
					"mahdi@gmail.com",
					22
			);
			studentRepository.save(student2);
			Student student3 = new Student(
					"negin",
					"mohammadi",
					"negin@gmail.com",
					28
			);
			studentRepository.save(student3);
		};
	}

}
