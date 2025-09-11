package com.assignment2.spring_demo;

import com.assignment2.spring_demo.models.DefaultMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringDemoApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SpringDemoApplication.class, args);
        ApplicationContext context = new ClassPathXmlApplicationContext("defaultMessageBean.xml");

        DefaultMessage defaultMessage = (DefaultMessage) context.getBean("defaultMessageBean");
        System.out.println(defaultMessage.getMessage());
	}

}
