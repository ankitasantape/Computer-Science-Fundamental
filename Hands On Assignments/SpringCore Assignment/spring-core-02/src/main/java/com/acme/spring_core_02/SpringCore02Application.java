package com.acme.spring_core_02;

import com.acme.spring_core_02.model.DefaultMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringCore02Application {

	public static void main(String[] args) {
//		SpringApplication.run(SpringCore02Application.class, args);

        ApplicationContext context = new ClassPathXmlApplicationContext("defaultMessageBean.xml");

        DefaultMessage defaultMessage = (DefaultMessage) context.getBean("defaultMessageBean");
        System.out.println(defaultMessage.getMessage());
	}


}
