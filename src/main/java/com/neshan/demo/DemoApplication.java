package com.neshan.demo;

import com.neshan.demo.Configurations.MyConfiguration;
import com.neshan.demo.Domain.MyBean;
import com.neshan.demo.Domain.MyProperties;
import com.neshan.demo.Domain.Student;
import com.neshan.demo.Repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(MyProperties.class)
public class DemoApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, MyProperties myProperties){
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
			System.out.println(myProperties);
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(MyConfiguration.class);
			ctx.refresh();

			MyBean mb1 = ctx.getBean(MyBean.class);
			System.out.println(mb1.hashCode());

			MyBean mb2 = ctx.getBean(MyBean.class);
			System.out.println(mb2.hashCode());

			ctx.close();
		};
	}

}
