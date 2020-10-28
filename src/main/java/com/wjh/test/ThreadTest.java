package com.wjh.test;

import com.wjh.util.ThreadUtil;

public class ThreadTest {
    public static void main(String[] args) {
        final Integer[] a = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    ThreadUtil.sleep(300, Thread.currentThread());
                    if (a[0] != null) {
                        System.out.println("break");
                        break;
                    }
                }
            }
        }).start();

        ThreadUtil.sleep(1000 * 2, Thread.currentThread());
        a[0] = 2;
        System.out.println("main-i=" + a[0]);
    }
}
