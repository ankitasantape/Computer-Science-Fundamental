package com.acme.spring_core_03;

import com.acme.spring_core_03.model.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringCore03Application {

	public static void main(String[] args) {
//		SpringApplication.run(SpringCore03Application.class, args);

        ApplicationContext context = new ClassPathXmlApplicationContext("studentBean.xml");

        Student student1 = (Student) context.getBean("student1");
        student1.display();

        Student student2 = (Student) context.getBean("student2");
        student2.display();
	}

}
