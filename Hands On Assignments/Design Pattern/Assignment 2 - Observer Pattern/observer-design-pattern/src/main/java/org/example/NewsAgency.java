package org.example;

import java.util.ArrayList;
import java.util.List;

public class NewsAgency implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private String news;

    public void setNews(String news){
        this.news = news;
        notifyObservers();
    }

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void remove(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update(news);
        }
    }
}
