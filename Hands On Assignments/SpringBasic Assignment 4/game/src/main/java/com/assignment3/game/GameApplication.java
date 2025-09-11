package com.assignment3.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class GameApplication {

	public static void main(String[] args) {
//		SpringApplication.run(GameApplication.class, args);
        ApplicationContext context = new ClassPathXmlApplicationContext("playerBean.xml");

        Player p1 = (Player) context.getBean("player1");
        Player p2 = (Player) context.getBean("player2");
        Player p3 = (Player) context.getBean("player3");
        Player p4 = (Player) context.getBean("player4");
        Player p5 = (Player) context.getBean("player5");

        Player[] players = {p1, p2, p3, p4, p5};

        Scanner sc = new Scanner(System.in);

        System.out.println("Show players from which country?");
        System.out.println(" (1) India");
        System.out.println(" (2) Australia");
        int choice = sc.nextInt();

        String countryName = (choice == 1) ? "India" : "Australia";

        System.out.println("\nPlayers from " + countryName + ":\n");

        for (Player p : players){
            if(p.getCountry().getCountryName().equalsIgnoreCase(countryName)){
                p.display();
            }
        }
	}

}
