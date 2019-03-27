package com.bupin.test;

public class App {
    public static void main(String[] args) {
        ObserableImpl obserable = new ObserableImpl();
        ObserverImpl observer1 = new ObserverImpl("observer1");
        ObserverImpl observer2 = new ObserverImpl("observer2");
        ObserverImpl observer3 = new ObserverImpl("observer3");
        obserable.attach(observer1);
        obserable.attach(observer1);
        obserable.attach(observer1);
        obserable.attach(observer2);
        obserable.attach(observer2);
        obserable.attach(observer3);
        obserable.setMessage("hello");
        obserable.detach(observer1);
        obserable.setMessage("world");
    }
}
