package com.assignment1.movie_app;

import com.assignment1.movie_app.model.Movie;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class MovieAppApplication {

	public static void main(String[] args) {
//		SpringApplication.run(MovieAppApplication.class, args);
        ApplicationContext context = new ClassPathXmlApplicationContext("movieBean.xml");

        Movie movie = (Movie) context.getBean("movieBean");
        System.out.println("Movie ID :" + movie.getMovieId());
        System.out.println("Movie Name : " + movie.getMovieName());
        System.out.println("Movie Actor : "+ movie.getMovieActor());

	}


}
