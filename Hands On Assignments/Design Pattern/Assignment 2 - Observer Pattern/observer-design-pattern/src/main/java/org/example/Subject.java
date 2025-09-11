package org.example;

public interface Subject {

     void register(Observer o);

     void remove(Observer o);

     void notifyObservers();
}
