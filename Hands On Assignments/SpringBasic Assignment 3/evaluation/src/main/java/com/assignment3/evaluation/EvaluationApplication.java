package com.assignment3.evaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class EvaluationApplication {

	public static void main(String[] args) {
//		SpringApplication.run(EvaluationApplication.class, args);

        ApplicationContext context = new ClassPathXmlApplicationContext("studentBean.xml");

        Student student1 = (Student) context.getBean("student1");
        student1.display();

        Student student2 = (Student) context.getBean("student2");
        student2.display();

    }

}
