package com.acme.spring_core_01;

import com.acme.spring_core_01.model.Movie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringCore01Application {

	public static void main(String[] args) {
//		SpringApplication.run(SpringCore01Application.class, args);

        ApplicationContext context = new ClassPathXmlApplicationContext("movieBean.xml");

        Movie movie = (Movie) context.getBean("movieBean");
        System.out.println("Movie ID :" + movie.getMovieId());
        System.out.println("Movie Name : " + movie.getMovieName());
        System.out.println("Movie Actor : "+ movie.getMovieActor());
	}

}
