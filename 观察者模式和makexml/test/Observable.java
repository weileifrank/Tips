package com.bupin.test;

public interface Observable {
    void attach(Observer observer);
    void detach(Observer observer);
    void broadcast();
}
