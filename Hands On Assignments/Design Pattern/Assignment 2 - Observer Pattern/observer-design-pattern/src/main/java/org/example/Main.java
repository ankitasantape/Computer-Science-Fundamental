package org.example;

public class Main {
    public static void main(String[] args) {
         NewsAgency agency = new NewsAgency();

         NewsChannel channel1 = new NewsChannel("BBC");
         NewsChannel channel2 = new NewsChannel("CNN");

         agency.register(channel1);
         agency.register(channel2);

         agency.setNews("Elections 2025: Result are out!");
         agency.setNews("Breaking: New Tech Regulations Announced!");

//       Unsubscribe one channel
         agency.remove(channel1);

         agency.setNews("Sports: Local team wins championship!");
    }
}