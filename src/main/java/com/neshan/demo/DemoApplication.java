package com.neshan.demo;

import com.neshan.demo.Configurations.MyConfiguration;
import com.neshan.demo.Domain.MyBean;
import com.neshan.demo.Domain.MyProperties;
import com.neshan.demo.Domain.Student;
import com.neshan.demo.Repositories.StudentRepository;
import com.neshan.demo.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//@EnableScheduling
@EnableConfigurationProperties(MyProperties.class)
@EnableAsync
public class DemoApplication extends WebSecurityConfigurerAdapter {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
//	@Bean
//	CommandLineRunner commandLineRunner(StudentRepository studentRepository, MyProperties myProperties){
//		return args -> {
//			Student student = new Student(
//					"ali",
//					"ghasemi",
//					"ali@gmail.com",
//					14
//			);
//			studentRepository.save(student);
//			Student student2 = new Student(
//					"mahdi",
//					"py",
//					"mahdi@gmail.com",
//					22
//			);
//			studentRepository.save(student2);
//			Student student3 = new Student(
//					"negin",
//					"mohammadi",
//					"negin@gmail.com",
//					28
//			);
//			studentRepository.save(student3);
//			System.out.println(myProperties);
//			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//			ctx.register(MyConfiguration.class);
//			ctx.refresh();
//
//			MyBean mb1 = ctx.getBean(MyBean.class);
//			System.out.println(mb1.hashCode());
//
//			MyBean mb2 = ctx.getBean(MyBean.class);
//			System.out.println(mb2.hashCode());
//
//			ctx.close();
//		};
//	}

	@Autowired
	UserService userService;

	@Bean
	PasswordEncoder bcryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider(){
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(bcryptPasswordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.userService);
		return daoAuthenticationProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/addrole").permitAll()
				.antMatchers("/adduser").permitAll()
				.antMatchers("/greeting").hasAnyRole("ADMIN")
				.anyRequest().authenticated().and().httpBasic();
	}

}
