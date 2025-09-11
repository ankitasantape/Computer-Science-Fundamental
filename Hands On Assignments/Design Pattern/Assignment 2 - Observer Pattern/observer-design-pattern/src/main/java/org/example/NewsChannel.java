package org.example;

public class NewsChannel implements Observer{

    private String name;
    private String latestNews;

    public NewsChannel(String name){
        this.name = name;
    }

    @Override
    public void update(String news) {
        this.latestNews = news;
        System.out.println("Channel " + name + " received news update: " + news);
    }

    public String getLatestNews(){
        return latestNews;
    }
}
