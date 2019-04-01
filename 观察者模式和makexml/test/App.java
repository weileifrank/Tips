package com.bupin.test;

import java.util.Random;

public class App {
    public static void main(String[] args) {

        int[] arr = {8, 3, 6, 4, 2, 0, 9, 1, 7, 5};
        int tmp;
//        for (int i = 0; i < arr.length - 1; i++) {
//            for (int j = 0; j < arr.length - 1 - i; j++) {
//                if (arr[j] > arr[j + 1]) {
//                    tmp = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = tmp;
//                }
//            }
//        }
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        for (int i : arr) {
            System.out.println(i);
        }

        //实现shuffle打乱的算法
//        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
//        int index, t;
//
//        for (int i = 0; i < arr.length; i++) {
//            index = new Random().nextInt(arr.length);
//            t = arr[i];
//            arr[i] = arr[index];
//            arr[index] = t;
//        }
//        for (int i : arr) {
//            System.out.print(i + ",");
//        }

//        ObserableImpl obserable = new ObserableImpl();
//        ObserverImpl observer1 = new ObserverImpl("observer1");
//        ObserverImpl observer2 = new ObserverImpl("observer2");
//        ObserverImpl observer3 = new ObserverImpl("observer3");
//        obserable.attach(observer1);
//        obserable.attach(observer1);
//        obserable.attach(observer1);
//        obserable.attach(observer2);
//        obserable.attach(observer2);
//        obserable.attach(observer3);
//        obserable.setMessage("hello");
//        obserable.detach(observer1);
//        obserable.setMessage("world");
    }

}
