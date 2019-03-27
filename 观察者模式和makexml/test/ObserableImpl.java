package com.bupin.test;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class ObserableImpl implements Observable {

//    private LinkedList<Observer> mLinkedList = new LinkedList<Observer>();
    private Set<Observer> mSet = new LinkedHashSet<>();
    private String mMessage;
    @Override
    public void attach(Observer observer) {
        mSet.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        if (!mSet.isEmpty()) {
            mSet.remove(observer);
        }
    }

    @Override
    public void broadcast() {
        for (Observer observer : mSet) {
            observer.update(mMessage);
        }
    }

    public void setMessage(String message) {
        this.mMessage = message;
        broadcast();
    }
}
