package com.springboot.h2.university;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class UniversityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityApplication.class, args);
	}

    @Bean
    public CommandLineRunner testApp(StudentRepository repo){
        return args -> {
            repo.save(new Student("James"));
            repo.save(new Student("Selena"));

            for (Student student : repo.findAll()){
                System.out.println("The student is: " + student.toString());
            }

            Optional<Student> james = repo.findById(1);
            System.out.println("James: " + james);
        };
    }

}
