package com.bupin.test;

public class ObserverImpl implements Observer {

    private String mName;
    private String mMessage;

    public ObserverImpl(String name) {
        mName = name;
    }

    @Override
    public void update(String message) {
        this.mMessage = message;
        System.out.println(mName+"收到的消息:"+mMessage);
    }
}
